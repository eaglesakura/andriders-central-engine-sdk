# Andriders Central Engine SDK
| ブランチ | 内容 | ビルドステータス |
|---|---|---|
| develop | 最新開発ブランチ(Nightly Build対象) | [![Circle CI](https://circleci.com/gh/eaglesakura/andriders-central-engine-sdk/tree/develop.svg?style=svg)](https://circleci.com/gh/eaglesakura/andriders-central-engine-sdk/tree/develop) |
| v3.0.x | ACE ver 3系列用SDK(予定) | [![Circle CI](https://circleci.com/gh/eaglesakura/andriders-central-engine-sdk/tree/v3.0.x.svg?style=svg)](https://circleci.com/gh/eaglesakura/andriders-central-engine-sdk/tree/v3.0.x) |

[@eaglesakura](https://twitter.com/eaglesakura)が開発しているサイクルコンピューターアプリ・[Andriders Central Engine(ACE)](https://play.google.com/store/apps/details?id=com.eaglesakura.andriders)と連携するためのSDKです。

このSDKを利用することで、ACEと下記の連携を行うことができます。

* ACEからユーザー本人、もしくはチームメンバーのサイクルコンピューター情報を取得する
	* 心拍
	* ケイデンス
	* GPS座標
	* 速度
		* GPS由来、ケイデンスセンサー由来
* ユーザーのコマンド入力に対するハンドリングを行う
	* 近接コマンド(スマホに手をかざす）
	* タイマーコマンド（一定時間間隔で実行する）
	* 距離コマンド（一定距離間隔で実行する）
	* 速度コマンド（ある速度条件を満たしたら実行する）
* v3.0.x以降で利用可能
 * 任意の内容を表示
* 詳細ドキュメント
 * [JavaDoc](http://eaglesakura.github.io/maven/doc/andriders-central-engine-sdk/javadoc/)

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
		// バージョンは基本的にAndriders Central Engineのバージョンに対応します
		// ACEバージョンが3.1の場合は3.1.+を指定するのが望ましいです。
    compile "com.eaglesakura:andriders-central-engine-sdk:3.0.+"
}
</pre>

## ライセンス

[MIT ライセンス](LICENSE-MIT.txt)	として公開します。ソースコードは自由に使ってもらって構いませんが、どんな不具合があっても責任は持ちません。

## ドキュメント

[Andriders Central Engineサイト](https://sites.google.com/site/andriderscentralengine/home)の「拡張機能開発」の手順に従ってください。
