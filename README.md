# Пример внедрения SDK платежей RuStore
## [Документация SDK платежей](https://www.rustore.ru/help/sdk/payments/general)

## Оглавление

- [Описание,сценарий использования](#Описание-сценарий-использования)
- [Сборка примера приложения](#Cборка-примера-приложения)
   - [Получение необходимой конфигурации](#Получение-необходимой-конфигурации)
   - [Настройка примера приложения](#Настройка-примера-приложения)
   - [Требуемые условия для корректного запуска примера](#Требуемые-условия-для-корректного-запуска-примера)
- [Лицензия](#Лицензия)
- [Есть вопросы](#Есть-вопросы)

## Описание, сценарий использования
ЧТО ЖЕ ТУТ БЛЭТ ВСЕ ТАКИ ДОЛЖНО БЫТЬ!!!!!

## Сборка примера приложения

### Получение необходимой конфигурации
Для корректной настройки примера приложения вам следует подготовить:

1. `consoleApplicationId` - код приложения из консоли разработчика RuStore (пример: https://console.rustore.ru/apps/123456), тут `consoleApplicationId` = 123456
2. `deeplinkScheme` - cхема deeplink, необходимая для возврата в ваше приложение после оплаты через стороннее приложение (например, SberPay или СБП). SDK генерирует свой хост к данной схеме. Важно, чтобы схема deeplink, передаваемая в deeplinkScheme, совпадала со схемой, указанной в AndroidManifest.xml в разделе “Обработка deeplink”.
3. `applicationId` -  из apk-файла, который вы публиковали в консоль RuStore, находится в файле build.gradle вашего проекта
```
android {
   defaultConfig {
   applicationId = "ru.rustore.sdk.billingexample" // 
   }
}
```

###  Настройка примера приложения
Для проверки работы приложения вы можете воспользоваться функционалом [тестовых платежей](https://www.rustore.ru/help/developers/monetization/sandbox).

- Указать `consoleApplicationId` и `deeplinkScheme` своего приложения в `RuStoreBillingClientFactory.create()`:
```
val billingClient = RuStoreBillingClientFactory.create(
context = context,
consoleApplicationId = "111111", // Заменить на свой id (https://console.rustore.ru/apps/111111)
deeplinkScheme = "rustoresdkexamplescheme", // Укажите URL-адрес для использования deeplink. Должен совпадать с <data android:scheme="" />
)
```
---


- В файле `AndroidManifest.xml` в параметре `data android:scheme` укажите URL-адрес для использования deeplink (должен совпадать с параметром deeplinkScheme из пункта 1)
```
<intent-filter>
   <action android:name="android.intent.action.VIEW" />
                <data android:scheme="rustoresdkexamplescheme" /> // Заменить на свой deeplink
            </intent-filter>
```
---


- В `BillingExampleViewModel` в переменной `availableProductIds` перечислите [подписки](https://www.rustore.ru/help/developers/monetization/create-app-subscription/) и [разовые покупки](https://www.rustore.ru/help/developers/monetization/create-paid-product-in-application/) доступные в вашем приложении:
```
private val availableProductIds = listOf(
        "productId1",
        "productId2",
        "productId3"
      )
```
---


- В директории `cert` замените сертификат `release.keystore` - сертификатом своего приложения, так же в `release.properties` выполните настройку параметров `key_alias`, `key_password`, `store_password`.


---
- Замените applicationId, в файле build.gradle, на applicationId apk-файла, который вы публиковали в консоль RuStore:
```
android {
    defaultConfig {
        applicationId = "ru.rustore.sdk.billingexample" // Зачастую в buildTypes приписывается .debug
    }
}
```
---


- Запустите проект и проверьте работу приложения

## Требуемые условия для корректного запуска примера

Для корректной работы SDK необходимо соблюдать следующие условия:
- Задан правильно `consoleApplicationId` в create():
```
val billingClient = RuStoreBillingClientFactory.create(
    context = context,
    consoleApplicationId = "111111", // Заменить на свой id (https://console.rustore.ru/apps/111111)
    deeplinkScheme = "yourappscheme", // Должен совпадать с <data android:scheme="" />
)
```
- `applicationId`, указанный в `build.gradle`, совпадает с `applicationId` apk-файла, который вы публиковали в консоль RuStore:
```
android {
    defaultConfig {
        applicationId = "ru.rustore.sdk.billingexample" // Зачастую в buildTypes приписывается .debug
    }
}
```
- Подпись `release.keystore` должна совпадать с подписью, которой было подписано приложение, опубликованное в консоль RuStore. Убедитесь, что используемый `buildType` (пр. debug) использует такую же подпись, что и опубликованное приложение (пр. release).

## Лицензия
Распространяется по лицензии MIT. Дополнительную информацию смотреть в файле `MIT-LICENSE.txt`

## Есть вопросы
Если появились вопросы по интеграции SDK платежей, обратитесь по этой ссылке:
[help.rustore.ru](https://help.rustore.ru/) или напишите на почту support@rustore.ru.
