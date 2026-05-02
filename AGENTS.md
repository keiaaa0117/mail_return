# AGENTS.md

このリポジトリは、Spring Boot + Thymeleaf + Spring Data JPA で構成されたメール管理アプリです。実装や修正を行うときは、既存のパッケージ構成と命名に合わせてください。

## コーディング規約

- Java 21 / Spring Boot / Maven Wrapper を前提にする。依存関係の追加は必要性が明確な場合だけ `pom.xml` に追加する。
- インデントはスペース4つ、クラス・メソッドの `{` は既存コードと同じく宣言行に置く。
- DI はフィールドインジェクションではなく、`private final` フィールド + コンストラクタインジェクションを使う。
- Controller にはリクエスト/レスポンスの受け渡し、画面遷移、HTTPステータス制御だけを書く。業務ロジックやDB操作は原則 Service に置く。
- DB更新を行う Service メソッドには `@Transactional` を付ける。参照のみの処理は必要に応じて読み取り専用トランザクションを検討する。
- APIの入力値検証は request DTO に `jakarta.validation` のアノテーションを書き、Controller 側で `@Valid` を付ける。
- JPA Entity はDBの永続化モデルとして扱い、APIレスポンスには `dto.response` のクラスを返す。
- 文字コードは UTF-8 を前提にする。コメント、バリデーションメッセージ、`@DisplayName` などで文字化けを増やさない。
- `target/` はビルド生成物なので、手作業で編集しない。

## アーキテクチャ

基本パッケージは `com.example.mail_return` です。新しい処理は以下の責務に沿って配置してください。

- `src/main/java/com/example/mail_return/MailReturnApplication.java`
  - Spring Boot の起動クラス。通常は変更しない。
- `src/main/java/com/example/mail_return/controller`
  - HTTPエンドポイントを置く。
  - JSON API は `@RestController` として `/api/...` に配置する。
  - Thymeleaf画面は `@Controller` として画面表示、フォーム受信、リダイレクトを扱う。
  - 既存例: `CreateMailController`, `GetMailListController`, `UpdateMailController`, `DeleteMailController`, `ViewController`
- `src/main/java/com/example/mail_return/service`
  - 業務ロジック、トランザクション境界、Repository 呼び出しを置く。
  - 作成・一覧・更新・論理削除など、既存コードはユースケース単位の Service に分かれている。
  - 既存例: `CreateMailService`, `GetMailListService`, `UpdateMailService`, `DeleteMailService`
- `src/main/java/com/example/mail_return/repository`
  - Spring Data JPA の Repository を置く。
  - DBアクセスは原則ここに集約し、Service から呼び出す。
  - 既存例: `MailRepository extends JpaRepository<MailEntity, Long>`
- `src/main/java/com/example/mail_return/entity`
  - JPA Entity を置く。
  - テーブル名、カラム定義、永続化ライフサイクル処理を記述する。
  - 既存例: `MailEntity` は `mails` テーブル、`active` による論理削除、`createdAt` / `updatedAt` を持つ。
- `src/main/java/com/example/mail_return/dto/request`
  - JSON API のリクエストDTOを置く。
  - バリデーションルールはここに書く。
  - 既存例: `CreateMailRequest`, `UpdateMailRequest`
- `src/main/java/com/example/mail_return/dto/response`
  - JSON API のレスポンスDTOを置く。
  - Entity をそのまま外へ返さず、必要な項目だけ詰め替える。
  - 既存例: `CreateMailResponse`, `GetMailListResponse`, `UpdateMailResponse`
- `src/main/java/com/example/mail_return/form`
  - Thymeleaf フォームの入力値を受ける画面用オブジェクトを置く。
  - 既存例: `MailForm`
- `src/main/resources/templates`
  - Thymeleaf テンプレートを置く。
  - 既存例: `mail_top.html`, `mail_add.html`, `mail_edit.html`
- `src/main/resources/static`
  - CSS、JavaScript などの静的ファイルを置く。
  - 既存例: `static/css/style.css`, `static/js/modal.js`
- `src/main/resources/application.properties`
  - アプリケーション設定を置く。現在はH2、JPA、H2 Consoleの設定がある。
- `src/test/java/com/example/mail_return`
  - JUnit 5 / Spring Boot Test / MockMvc のテストを置く。

## 命名規則

- パッケージ名は既存の `com.example.mail_return` 配下に合わせる。
- クラス名は PascalCase にする。
- Controller は `{ユースケース}MailController` または画面系の集約として `ViewController` のように命名する。
  - 例: `CreateMailController`, `UpdateMailController`
- Service は `{ユースケース}MailService` とする。
  - 例: `CreateMailService`, `GetMailListService`
- Repository は `{Entity名からEntityを除いた名前}Repository` とする。
  - 例: `MailRepository`
- Entity は `{ドメイン名}Entity` とする。
  - 例: `MailEntity`
- request DTO は `{ユースケース}MailRequest`、response DTO は `{ユースケース}MailResponse` とする。
  - 例: `CreateMailRequest`, `GetMailListResponse`
- 画面フォームは `{ドメイン名}Form` とする。
  - 例: `MailForm`
- メソッド名、変数名、フィールド名は lowerCamelCase にする。
  - 例: `createMail`, `getMailList`, `logicalDelete`, `updatedAt`
- Repository の検索メソッドは Spring Data JPA の命名規則に合わせる。
  - 例: `findByActiveTrue()`
- Thymeleaf テンプレート名と静的ファイル名は既存に合わせて小文字または snake_case にする。
  - 例: `mail_top.html`, `modal.js`

## テスト方針

- テストは `src/test/java/com/example/mail_return` 配下に置く。
- APIの振る舞いは `@SpringBootTest` + `@AutoConfigureMockMvc` + `MockMvc` で確認する。
- CRUD APIを変更した場合は、正常系だけでなく以下も追加・更新する。
  - バリデーションエラー時のHTTPステータスとレスポンス
  - 存在しないIDを指定した場合
  - 論理削除済みデータが一覧や更新対象から外れること
- テストデータはテスト間で依存しないようにする。必要に応じて Repository のクリーンアップ、`@Transactional`、`@DirtiesContext`、テスト専用プロファイルを使う。
- 画面系を変更した場合は、主要なURLが200を返すこと、フォーム送信後に期待するリダイレクトになることを MockMvc で確認する。
- Service の分岐や例外処理が増えた場合は、Controller 経由のテストに加えて Service 単体寄りのテストも検討する。
- 変更後は原則として次を実行する。

```powershell
.\mvnw.cmd test
```
