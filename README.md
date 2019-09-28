# dataforms.jar Java web application framework 2　(開発中).

## 開発方針

dataforms.jarのver.2です。当面は開発中ですので、リリースは行いません。
dataforms.jarのJDK-11/ES2015(ES6)対応バージョンと考えてください。
ver.1とは互換性もなく、IEのサポートはしない予定です。
当分IE11の対応を求められるケースも多いので、しばらくはVer.1.xxを使っていく方針です。
ver.1.xxは実務で使っているため、ある程度互換性を考慮して改善していきますが、
ver.2はver.1.xxの反省点を取り込む形で思い切って変更していく予定です。


## Description
Java Webアプリケーションフレームワークと、その開発ツールです。
Javaのクラスライブラリ、Javascriptのクラスライブラリ、開発ツール、ドキュメントが全て1つのjarファイルに入っています。

特徴を以下にまとめます。

* 習得するのに必要な知識は、HTML,Java,Javascript,jQueryくらいです。SQLの基本を押さえておけば、Daoクラス関連の機能もすぐに理解できると思います。
* 依存ライブラリ(jQuery,jQuery-ui,jsonic,apache-commonsのいくつかとpoi,apache FOP)は少なく、シンプルな構造です。
* JSPを使用せず、HTMLをそのまま使用します。
* dataforms.jarが自動生成するJavascriptが、HTML中のイベントハンドラを適切に設定します。そのため、HTMLにはJavascriptやonxxx等のイベントアトリビュートを一切記述しません。
* 開発ツールを装備し、とりあえず動作するJava,Javascript,HTMLを自動生成することができます。
* データベースのテーブルや問い合わせは、JavaのTable,Queryクラスで定義するため、ほとんどSQLの記述は不要です。
* データベースのテーブル作成やテーブル構造の変更は、開発ツールで簡単に行うことができます。
* 複数のベースサーバに対応し、データベースサーバに依存しないアプリケーションの構築が可能です。(開発環境は組み込みApache Derby、運用はPostgreSQLというシステム開発の実績があります。)
* デフォルトのフレームはレスポンシブデザインになっており、1つのHTMLでPC,タブレット,スマートフォンの画面サイズに対応します。
* フレームデザインは単純なHTML,CSSで記述してあるので、簡単にカスタマイズすることができます。


## References
[ドキュメント、サンプル等へのリンク](http://woontai.dip.jp/dataforms.jar/)

## Install
インストールの手順をまとめると以下のようになります。

* [Pleiades - Eclipse プラグイン日本語化プラグイン](http://mergedoc.osdn.jp/index.html#pleiades.html)をダウンロードしインストール。
* Pleiadesに付属するtomcat8のlibフォルダに[Apache Derby](https://db.apache.org/derby/)からダウンロードした組み込みDerbyのドライバ(derby.jar,derby_ja_JP.jar)をコピー。
* EclipseのサーバービューにTomcat8(java8)を追加。
* [リリース](https://github.com/takayanagi2087/dataforms/releases)から、dfblank_xxx.warファイルをダウンロードし、Eclipseでインポート。
* Tomcat8(Java8)にインポートしたプロジェクトを追加し、クリーンビルドの後Tomcat8を起動。
* ブラウザからアプリをアクセスし、開発者ユーザを登録。

詳細は[ドキュメント](http://woontai.dip.jp/dfsample/dataforms/devtool/page/doc/DocFramePage.df)を参照してください。


## Demo
ドキュメントに記述されているサンプルは、以下のデモサイトで動作しています。
[サンプルページ](http://woontai.dip.jp/dfsample/sample/page/SamplePage.df)
ドキュメントで作成するSamplePageはログインしないと表示されないページですが、デモページではその制限を外しています。

## Requirement
主に、Eclipse201906(4.12) + Java11 + Tomcat9 + Apache Derby,PostgreSQLでテストしています。
Servlet 3.0に対応したアプリケーションサーバで動作するはずです。

対応しているデータベースサーバは、以下の通りです。(バージョンは実績のあるバージョンを記載しています。)

* Apache Derby 10.11.1.1
* PostgreSQL 8.4.20, 9.2.7
* MariaDB(MySQL) 5.5.37
* Oralce11g 11.2.0.1.0


## Licence
[MIT](https://github.com/takayanagi2087/dataforms/blob/master/LICENSE)


