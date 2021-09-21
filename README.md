<<<<<<< HEAD
# triprequest
=======
# Triprequest

## 概要

2018 年度学部 4 年武輪の研究内容です。 \
大学の出張旅費申請の効率化のための Web アプリケーションのサーバーサイドコードです。 \
SpringBoot, maven を使っています。 \
 \
フロントエンドのコードもあわせて確認ください。 https://github.com/yam-lab/triprequest-frontend \
Droolsでルールを実装したデータはhikiに上がってます。 http://www.yamaguti.comp.ae.keio.ac.jp/cgi-bin/hiki/?%C9%F0%CE%D8+%BD%D3%B0%EC

## 使い方

maven を使った SpringBoot の標準構成だと思うので基本的なところは割愛します。 \
maven と SpringBoot でググれば出てくると思います。
起動: ./mvnw spring-boot:run

### Drools(kie-server)との連携

- src/main/java/com/officeai/triprequest/util/KieServerClient.java の`DEPLOY_UNIT_NAME`と`KIE_SERVER_URL`は自分の環境に合わせて適宜変更してください
- drools のルール変更でデータオブジェクトのプロパティに変更があった場合
  - drools で jar ファイルをダウンロードする
  - lib 配下に jar ファイルを置く
  - pom.xml の最後の dependency の`version`と`systemPath`を更新する
  - maven の install コマンドを叩く

>>>>>>> 73c5d1f (エクセル出力まで)
