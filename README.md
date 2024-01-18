# Пример внедрения SDK платежей RuStore
## [Документация SDK платежей](https://www.rustore.ru/help/sdk/payments/general)

<!-- TABLE OF CONTENTS -->
<details>
  <summary>ОГЛАВЛЕНИЕ</summary>
  <ol>
    <li><a href="#описание-сценарий-использования">Описание, сценарий использования</a></li>
    <li>
      <a href="#настройка-примера-приложения">Настройка примера приложения</a>
      <ul>
        <li><a href="#получение-необходимых-параметров">Получение необходимых параметров</a></li>
        <li><a href="#настройка-приложения">Настройка приложения</a></li>
      </ul>
    </li>
    <li><a href="#лицензия">Лицензия</a></li>
    <li><a href="#есть-вопросы">Есть вопросы</a></li>
  </ol>
</details>


## Описание, сценарий использования
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)

## Настройка примера приложения

### Получение необходимых параметров
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)
Что нужно сделать, чтобы получить ид, ключи, какие настройки нужно указать в консоли и тд(этот блок копируется между SDK)

###  Настройка приложения
Для проверки работы приложения вы можете воспользоваться функционалом [тестовых платежей](https://www.rustore.ru/help/developers/monetization/sandbox).

ТУТ ЕЩЕ НАДО УКАЗАТЬ ЧТО СЛЕДУЕТ СКАЧАТЬ СЕБЕ ПРИЛОЖЕНИЕ СНАЧАЛА

1. Указать `consoleApplicationId` и `deeplinkScheme` своего приложения в RuStoreBillingClientFactory.create():
```
val billingClient = RuStoreBillingClientFactory.create(
context = context,
consoleApplicationId = "111111", // Заменить на свой id (https://console.rustore.ru/apps/111111)
deeplinkScheme = "rustoresdkexamplescheme", // Укажите URL-адрес для использования deeplink. Должен совпадать с <data android:scheme="" />
)
```
2. В файле `AndroidManifest.xml` в параметре `data android:scheme` укажите URL-адрес для использования deeplink (должен совпадать с параметром deeplinkScheme из пункта 1)
```
<intent-filter>
   <action android:name="android.intent.action.VIEW" />
                <data android:scheme="rustoresdkexamplescheme" /> // Заменить на свой deeplink
            </intent-filter>
```
3. В `BillingExampleViewModel` в переменной `availableProductIds` перечислите [подписки](https://www.rustore.ru/help/developers/monetization/create-app-subscription/) и [разовые покупки](https://www.rustore.ru/help/developers/monetization/create-paid-product-in-application/) доступные в вашем приложении:
```
private val availableProductIds = listOf(
        "productId1",
        "productId2",
        "productId3"
      )
```
4. В директории "cert" замените сертификат `release.keystore` - сертификатом своего приложения, так же в `release.properties` выполните настройку параметров `key_alias`, `key_password`, `store_password`.
5. Замените applicationId, в файле build.gradle, на applicationId apk-файла, который вы публиковали в консоль RuStore:
```
android {
    defaultConfig {
        applicationId = "ru.rustore.sdk.billingexample" // Зачастую в buildTypes приписывается .debug
    }
}
```
6. Запустите проект и проверьте работу приложения

## Лицензия
Распространяется по лицензии MIT. Дополнительную информацию смотреть в файле `MIT-LICENSE.txt`

## Есть вопросы
Если появились вопросы по интеграции SDK платежей, обратитесь по этой ссылке:
[help.rustore.ru](https://help.rustore.ru/) или напишите на почту support@rustore.ru.
