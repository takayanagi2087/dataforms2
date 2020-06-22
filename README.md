# dataforms2.jar Java web application framework.

## Description
Java Webアプリケーションフレームワークと、その開発ツールです。
dataforms.jarのJDK-11/ES2015(ES6)対応バージョンです。
ES6に対応したため、ver.1とは互換性はありません。
IE11はデフォルトでは動作しない設定になっていますが、
サーバサイドにBabelをインストールすれば動作します。

特徴を以下にまとめます。

* Javaのクラスライブラリ、Javascriptのクラスライブラリ、開発ツール、ドキュメントが全て1つのjarファイルに入っています。
* 習得するのに必要な知識は、HTML,Java,Javascript,jQueryくらいです。SQLの基本を押さえておけば、Daoクラス関連の機能もすぐに理解できると思います。
* 依存ライブラリ(jQuery,jQuery-ui,jsonic,apache-commonsのいくつかとPOI,apache FOP)は少なく、シンプルな構造です。
* JSPを使用せず、HTMLをそのまま使用します。
* dataforms2.jarが自動生成するJavascriptが、HTML中のイベントハンドラを適切に設定します。そのため、HTMLにはJavascriptやonxxx等のイベントアトリビュートを一切記述しません。
* 開発ツールを装備し、とりあえず動作するJava,Javascript,HTMLを自動生成することができます。
* データベースのテーブルや問い合わせは、JavaのTable,Queryクラスで定義するため、ほとんどSQLの記述は不要です。
* データベースのテーブル作成やテーブル構造の変更は、開発ツールで簡単に行うことができます。
* 複数のベースサーバに対応し、データベースサーバに依存しないアプリケーションの構築が可能です。(開発環境は組み込みApache Derby、運用はPostgreSQLというシステム開発の実績があります。)
* デフォルトのフレームはレスポンシブデザインになっており、1つのHTMLでPC,タブレット,スマートフォンの画面サイズに対応します。
* フレームデザインは単純なHTML,CSSで記述してあるので、簡単にカスタマイズすることができます。


## Install
インストールの手順をまとめると以下のようになります。

* [Pleiades - Eclipse プラグイン日本語化プラグイン](http://mergedoc.osdn.jp/index.html#pleiades.html)をダウンロードしインストール。
* EclipseのサーバービューにTomcat9(java11)を追加。
* Pleiadesに付属するtomcat9のlibフォルダに[Apache Derby](https://db.apache.org/derby/)からダウンロードした組み込みDerbyのドライバをコピー。
* [javax.mail.jar](https://github.com/javaee/javamail/releases)と[javax.activation.jar](https://github.com/javaee/activation/releases)をtomcat9のlibフォルダにコピー。
* [リリース](https://github.com/takayanagi2087/dataforms2/releases)から、dfblank_xxx.warファイルをダウンロードし、Eclipseでインポート。
* Tomcat9(Java11)にインポートしたプロジェクトを追加し、プロジェクトをビルドした後Tomcat9を起動。
* ブラウザからアプリをアクセスし、開発者ユーザを登録。

詳細は[ドキュメント](http://woontai.dip.jp/dfblank/dataforms/devtool/doc/page/DocFramePage.df)の「2.開発環境構築」を参照してください。


## Requirement
主に、Eclipse(pleiades2020-03) + Java11 + Tomcat9 + Apache Derby,PostgreSQLでテストしています。
Servlet 3.0に対応したアプリケーションサーバで動作するはずです。

対応しているデータベースサーバは、以下の通りです。(バージョンは実績のあるバージョンを記載しています。)

* Apache Derby 10.14.2.0
* PostgreSQL 9.2.23-1
* MariaDB(MySQL) 5.5.56-2
* Oralce11g 11.2.0.1.0


## Licence
[MIT](https://github.com/takayanagi2087/dataforms/blob/master/LICENSE)


