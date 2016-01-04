package com.eaglesakura.andriders.extension.data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.eaglesakura.andriders.extension.ExtensionInformation;
import com.eaglesakura.andriders.extension.IExtensionService;
import com.eaglesakura.andriders.idl.remote.IdlHeartrate;
import com.eaglesakura.andriders.idl.remote.IdlSpeedAndCadence;
import com.eaglesakura.andriders.idl.remote.IdlStringProperty;
import com.eaglesakura.andriders.protocol.SensorProtocol;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.android.service.CommandMap;
import com.eaglesakura.android.service.CommandServer;
import com.eaglesakura.android.service.aidl.ICommandClientCallback;
import com.eaglesakura.util.LogUtil;

import java.util.List;

public class RemoteDataManager {

    final Context context;

    final IExtensionService service;

    CommandServerImpl server;

    CommandMap acesCommandMap = new CommandMap();

    public RemoteDataManager(IExtensionService service) {
        if (!(service instanceof Service)) {
            throw new IllegalStateException("service not impl!!");
        }
        this.context = ((Context) service).getApplicationContext();
        this.service = service;

        buildCommandMap();
    }

    private void validAces() {
        if (service == null || !isConnectedAces()) {
            throw new IllegalStateException("Not Connected!!");
        }
    }

    public IBinder onBind(Intent intent) {
        if (!IExtensionService.ACTION_ACE_EXTENSION_BIND.equals(intent.getAction())) {
            return null;
        } else if (server != null) {
            return server.getBinder();
        }

        // サーバーを起動する
        server = new CommandServerImpl((Service) service);
        return server.getBinder();
    }

    /**
     * ACEsに接続されていればtrueを返却する
     * <p/>
     * 設定画面から接続されている、もしくは接続されていない場合はfalseを返却する
     *
     * @return
     */
    public boolean isConnectedAces() {
        return server != null && server.hasClient(IExtensionService.CLIENT_ID_ACE_SERVICE);
    }

    /**
     * 接続先のMACアドレスを取得する
     * <p/>
     * このアドレスにしたがってデータを得る
     *
     * @param type
     * @return
     */
    public String getGadgetAddress(SensorProtocol.SensorType type) {
        validAces();

        IdlStringProperty idl = new IdlStringProperty(null);
        idl.setValue(type.toString());

        try {
            IdlStringProperty addr = server.postToAces(IdlStringProperty.class, CentralDataCommand.CMD_POST_QUERY_BLE_GADGET_ADDRESS, idl);
            return addr.getValue();
        } catch (Exception e) {
            LogUtil.log(e);
        }

        return null;
    }


    /**
     * 心拍を更新する
     *
     * @param bpm
     */
    public void setHeartrate(int bpm) {
        validAces();

        IdlHeartrate idl = new IdlHeartrate(null);
        idl.setBpm(bpm);

        try {
            server.postToAces(CentralDataCommand.CMD_POST_HEARTRATE, idl);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }

    /**
     * S&Cセンサーの情報を更新する
     *
     * @param crankRpm
     * @param crankRevolution
     * @param wheelRpm
     * @param wheelRevolution
     */
    public void setSpeedAndCadence(float crankRpm, int crankRevolution, float wheelRpm, int wheelRevolution) {
        validAces();

        IdlSpeedAndCadence idl = new IdlSpeedAndCadence(null);
        idl.setCrankRpm(crankRpm);
        idl.setCrankRevolution(crankRevolution);
        idl.setWheelRpm(wheelRpm);
        idl.setWheelRevolution(wheelRevolution);

        try {
            server.postToAces(CentralDataCommand.CMD_POST_SPEED_AND_CADENCE, idl);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }


    /**
     * ACESとのデータIOを行う
     */
    class CommandServerImpl extends CommandServer {
        public CommandServerImpl(Service service) {
            super(service);
        }

        public byte[] postToAces(String cmd, BaseProperties prop) throws RemoteException {
            byte[] postData = prop != null ? prop.toByteArray() : null;
            return postToClient(IExtensionService.ACES_PACKAGE_NAME, cmd, postData);
        }

        public <T extends BaseProperties> T postToAces(Class<T> clazz, String cmd, BaseProperties args) throws RemoteException {
            byte[] result = postToAces(cmd, args);
            return BaseProperties.deserializeInstance(null, clazz, result);
        }

        @Override
        protected byte[] onReceivedDataFromClient(String cmd, byte[] buffer) throws RemoteException {
            return acesCommandMap.execute(this, cmd, buffer);
        }

        @Override
        protected void onRegisterClient(String id, ICommandClientCallback callback) {
            if (IExtensionService.CLIENT_ID_ACE_SERVICE.equals(id)) {
                service.onAceServiceConnected(RemoteDataManager.this);
            }
        }

        @Override
        protected void onUnregisterClient(String id) {
            if (IExtensionService.CLIENT_ID_ACE_SERVICE.equals(id)) {
                service.onAceServiceDisconnected(RemoteDataManager.this);
            }
        }
    }

    private void buildCommandMap() {
        /**
         * 拡張機能情報を取得する
         */
        acesCommandMap.addAction(CentralDataCommand.CMD_PULL_EXTENSION_INFORMATIONS, new CommandMap.Action() {
            @Override
            public byte[] execute(Object sender, String cmd, byte[] buffer) throws Exception {
                List<ExtensionInformation> informations = service.getExtensionInformations();
                return ExtensionInformation.serialize(informations);
            }
        });
    }
}
