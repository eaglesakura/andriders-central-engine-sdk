# Andriders Central Engine SDK

[@eaglesakura](https://twitter.com/eaglesakura)が開発しているサイクルコンピューターアプリ・[Andriders Central Engine(ACE)](https://play.google.com/store/apps/details?id=com.eaglesakura.andriders)と連携するためのSDKです。

このSDKを利用することで、ACEと下記の連携を行うことができます。

* ACEからユーザー本人、もしくはチームメンバーのサイクルコンピューター情報を取得する
	* 心拍
	* ケイデンス
	* GPS座標
	* 速度
		* GPS由来、ケイデンスセンサー由来
	* 周辺情報
		* コンビニ等
	* ACEのチーム連携を行っている場合、リアルタイムでチームメンバーのサイクルコンピューター情報も取得可能
* ユーザーのコマンド入力に対するハンドリングを行う
	* 近接コマンド(スマホに手をかざす）
	* タイマーコマンド（一定時間間隔で実行する）
	* 距離コマンド（一定距離間隔で実行する）
	* 速度コマンド（ある速度条件を満たしたら実行する）

## build.gradle

* Maven Centralに追加するのがメンドウだったので、andriders-central-engine-sdkを使用するためにはgithubのリポジトリをbuild.gradleへ追加する必要があります。
<pre>
allprojects {
    repositories {
        // add repository
        maven { url "http://eaglesakura.github.io/maven/" }
    }
}
</pre>
<pre>
dependencies {
    // add library
    compile "com.eaglesakura:andriders-central-engine-sdk:0.4.+"
}
</pre>

## 依存ライブラリ

* 下記の外部ライブラリに依存します
	 * com.google.protobuf:protobuf-java:2.6.0
	 * com.android.support:support-annotations:+
	 * com.eaglesakura:eglibrary-java-geo:0.2.+
	 * com.eaglesakura:eglibrary-android-java-core:0.2.+

## ライセンス

[MIT ライセンス](LICENSE-MIT.txt)	として公開します。ソースコードは自由に使ってもらって構いませんが、どんな不具合があっても責任は持ちません。

## ドキュメント

[JavaDoc](http://eaglesakura.github.io/maven/doc/andriders-central-engine-sdk/javadoc/)

[Andriders Central Engineサイト](https://sites.google.com/site/andriderscentralengine/home)の「拡張機能開発」の手順に従ってください。
