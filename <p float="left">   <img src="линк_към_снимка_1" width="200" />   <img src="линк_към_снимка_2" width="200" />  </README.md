# Musify (MobileApps 2025)
[**Изтегли APK тук**](./apk/app-release.apk)

---
## Идея: 

*Интерактивен каталог за откриване и запазване на музикални артисти, поддържащ офлайн режим и мултиезичност.*

---

## **Как работи:** 

*Потребителите влизат в профила си, търсят изпълнители, преглеждат детайли (описание, концерти) и добавят любимците си в облачно "Избрано", което се синхронизира много бързно в благодарение на локалния кеш.*

---

## **Архитектура:** 

*Изградено с **MVVM** патърн и *Single Source*. Използва Jetpack Compose (UI), Room (локална БД), Firebase Auth & Firestore (облак) и DataStore (настройки).*

---

## **Потребителски поток:** 

`Splash` ➔ `Welcome` ➔ `Вход/Регистрация` ➔ `Начален екран` (Търсене / Избрани / Профил / Настройки) ➔ `Детайли за артист` / (`Настройки` ➔ `Излизане от профилата`)

---
### **Стартиране:** 

Клонирай хранилището. 
Отвори проекта с Android Studio. 
Добави своя `google-services.json` в папка `app` (за Firebase). 
Натисни *Run* (Shift+F10). 

### **Тестов акаунт:** 

Email: `test@test.com` 
Password: `123456`

### **Скрийншотове:** 

<p float="left">
  <img src="./apk/WeclomeScreen.png" width="200" />
  <img src="./apk/LoginScreen.png" width="200" />
  <img src="./apk/ResetPassword.png" width="200" />
  <img src="./apk/SignupScreen.png" width="200" />
  <img src="./apk/Mainscreen_1.png" width="200" />
  <img src="./apk/Mainscreen_2.png" width="200" />
  <img src="./apk/DetailSreen.png" width="200" />
  <img src="./apk/FavoritesTab.png" width="200" />
  <img src="./apk/ProfileTab.png" width="200" />
  <img src="./apk/SettingsTab.png" width="200" />
  
</p>




