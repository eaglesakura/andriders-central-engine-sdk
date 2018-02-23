package com.eaglesakura.andriders.serialize;

import com.eaglesakura.util.RandomUtil;

import java.util.Random;

public class RawSpecs {

    /**
     * アプリ情報
     */
    public RawAppSpec application;

    /**
     * 身体情報
     */
    public RawFitnessSpec fitness;

    public RawSpecs() {
    }

    @Deprecated
    public RawSpecs(Class<Random> dummy) {
        application = new RawAppSpec(dummy);
        fitness = new RawFitnessSpec(dummy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawSpecs rawSpecs = (RawSpecs) o;

        if (application != null ? !application.equals(rawSpecs.application) : rawSpecs.application != null)
            return false;
        return !(fitness != null ? !fitness.equals(rawSpecs.fitness) : rawSpecs.fitness != null);

    }

    @Override
    public int hashCode() {
        int result = application != null ? application.hashCode() : 0;
        result = 31 * result + (fitness != null ? fitness.hashCode() : 0);
        return result;
    }

    public static class RawAppSpec {

        public int protocolVersion;


        public String appVersionName;


        public String appPackageName;

        public RawAppSpec() {
        }

        @Deprecated
        public RawAppSpec(Class<Random> dummy) {
            protocolVersion = RandomUtil.randUInt8();
            appVersionName = RandomUtil.randString();
            appPackageName = RandomUtil.randString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawAppSpec that = (RawAppSpec) o;

            if (protocolVersion != that.protocolVersion) return false;
            if (appVersionName != null ? !appVersionName.equals(that.appVersionName) : that.appVersionName != null)
                return false;
            return !(appPackageName != null ? !appPackageName.equals(that.appPackageName) : that.appPackageName != null);

        }

        @Override
        public int hashCode() {
            int result = protocolVersion;
            result = 31 * result + (appVersionName != null ? appVersionName.hashCode() : 0);
            result = 31 * result + (appPackageName != null ? appPackageName.hashCode() : 0);
            return result;
        }
    }

    /**
     * ユーザーが入力したフィットネス情報
     */
    public static class RawFitnessSpec {
        /**
         * 体重
         */

        public float weight;

        /**
         * 通常心拍
         */

        public short heartrateNormal;

        /**
         * 最大心拍
         */

        public short heartrateMax;

        public RawFitnessSpec() {
        }

        @Deprecated
        public RawFitnessSpec(Class<Random> dummy) {
            weight = RandomUtil.randFloat();
            heartrateNormal = RandomUtil.randInt8();
            heartrateMax = RandomUtil.randInt8();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawFitnessSpec that = (RawFitnessSpec) o;

            if (Float.compare(that.weight, weight) != 0) return false;
            if (heartrateNormal != that.heartrateNormal) return false;
            return heartrateMax == that.heartrateMax;

        }

        @Override
        public int hashCode() {
            int result = (weight != +0.0f ? Float.floatToIntBits(weight) : 0);
            result = 31 * result + (int) heartrateNormal;
            result = 31 * result + (int) heartrateMax;
            return result;
        }
    }
}
