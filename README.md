# Architecture

This project is written in Kotlin. Uses concepts of Clean Architecture and Offline First.

MVVM is the default Design Pattern.

Kotlin Coroutines for Asynchronous Tasks.

Minimum Android SDK: API 21

```
+-------------------------------------+
|                                     |
|                 APP                 |
|                                     |
+-------------------------------------+
     |            |             |
     |            |             |
     v            v             v
+---------+  +---------+  +-----------+
|         |  |         |  |           |
|  AUTH   |  |  HOME   |  |  PROFILE  |
|         |  |         |  |           |
+---------+  +---------+  +-----------+
     |            |             |
     |            |             |
     v            v             v
+-------------------------------------+
|                                     |
|                 CORE                |
|                                     |
+-------------------------------------+
```

- ```App Module```: Splash Screen, Main Screen and Navigation Graphs. Knows all feature modules.

- ```Auth Module```: Auth Feature.

- ```Home Module```: Home Feature.

- ```Profile Module```: Profile Feature.

- ```Core Module```: Navigator, Navigation IDs, Custom Views, Datasources, Repositories. All core logic.

# Libraries

- ```Room``` for Database Implementation

- ```Glide``` for Image Processing

- ```Koin``` for Dependency Injection

- ```OkHttp``` for HTTP/HTTPS Requests

- ```Retrofit``` for API Mapping Datasources

- ```Gson``` for Json Parser

- ```Google Auth``` for Social Login with Google

- ```Facebook Login``` for Social Login with Facebook

# Gradle Builds

Builds are made with Kotlin DSL and generated in this directory:

```<ROOT_DIR>/builds/<MODULE_NAME>```

# KeyStore

Algorithm | Key Size | Cipher
----------|----------|---------------------
RSA       | 2048     | RSA/ECB/PKCS1Padding

With the ```KeyStore``` class it is possible to encrypt and decrypt bytes in a simple way, but we have a limitation.

The maximum number of bytes supported for encryption is calculated as follows:

```kotlin
floor(floor(KEY_SIZE.toDouble() / 8) - 11).toInt()
```

If we set ```KEY_SIZE = 2048```, we can only encrypt ```248 bytes``` or an exception will be thrown.

To work around this problem, we can "break" an array of bytes in equal sizes of ```248 bytes``` and encrypt part by part and concatenate the results.

For example. If we want to encrypt an array of bytes of size ```485 bytes```, we will break into two parts of at most ```248 bytes```:

```
248 + 237 = 485 bytes
```

Next, we will encrypt the first part (```248 bytes```) which will generates an array of bytes with a total of ```256 bytes```.

Finally, we will encrypt the second part (```237 bytes```) which will generates an array of bytes with a total of ```256 bytes```.

Concatenating the two parts will have an array of bytes with ```512 bytes```.

To decrypt this array resulting from the concatenation of these two parts, do the following:

```
512 / 2 = 256 bytes
```

The first part is decrypted, and then the second part is decrypted. Then the first part is concatenated with the second part. At the end, we will have the original byte array.

To encrypt an array of bytes use:

```kotlin
AppKeyStore.encode(ByteArray): ByteArray
```

To dencrypt an array of bytes use:

```kotlin
AppKeyStore.decode(ByteArray): ByteArray
```

# Dependency Injection

Essential dependency injections happen asynchronously during Splash Screen.

Each dependency injection happens gradually as the user navigates through the App.

# Network Monitor

If device is offline a message will be appear in the bottom of screen.

# Web Requests

Oauth 2 for authentication. 

Auto Refresh Token if necessary.

API responds with envelope:

```json
{
  "meta": {
    "message": ""
  },
  "body": {}
}
```

# Repositories

Writing...

# Exceptions

- ```AuthException``` for authentication errors.
- ```ConnectionException``` for connection errors.
- ```ServerException``` for server errors.
- ```UnauthorizedException``` for unauthorized error.
- ```SyncException``` for sync errors.

```kotlin
Throwable.toAppError(Context): AppError
```

# Preferences

Values are encrypted with the ```AppKeyStore```.

# Navigation

Between Activities:

```kotlin
Navigator.nav(Screen, Bundle)
```

Between Fragments:

```kotlin
Navigator.nav(NavController, Int, Bundle)
```

# Default Layouts

By default, views are included in the ```content``` container of the base layout.

### Activities:

All activity base layouts are implemented by ```IActivityLayoutView```. The default layout follows:

```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <!-- APP BAR -->
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar" 
        ... />

    <!-- CONTENT -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/content"
        ... />

    <!-- LOADING -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/loading"
        ... />

    <!-- MESSAGE -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/message"
        ... />

    <!-- INFO -->
    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/info"
        ... />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Fragments:

All fragment base layouts are implemented by ```ILayoutView```. The default layout follows:

```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <!-- CONTENT -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/fragment_content"
        ... />
            
    <!-- LOADING -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/fragment_loading"
        ... />

    <!-- MESSAGE -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/fragment_message"
        ... />
</androidx.constraintlayout.widget.ConstraintLayout>
```

To create a layout for an activity:

```xml
<com.domain.skeleton.core.view.activity.ActivityView>
    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/tv_label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</com.domain.skeleton.core.view.activity.ActivityView>
```

To create a layout for a fragment:

```xml
<com.domain.skeleton.core.view.fragment.FragmentView>
    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/tv_label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</com.domain.skeleton.core.view.activity.ActivityView>
```

# Default Views

All default app views are injected to all activities and fragments and are access by ```appViews``` attribute.

- ```ToolbarView``` is the default toolbar view.

- ```LoadingView``` is the default loading view.

- ```UnauthorizedView``` is the default unauthorized view.

- ```NoConnectionView``` id the default no connection message view.

- ```CustomErrorView``` is the default view for custom messages.

```kotlin
appViews.customError.message.text = "Message"
appViews.customError.setOnClickListener {
    myFunction.invoke()
}

rootView.setMessage(appViews.customError)
```

# 1. Configure your project

# 1.1. Google SDK

### 1.1.1. Create Project

```https://console.developers.google.com > New Project```

### 1.1.2. Enable API

```Dashboard > Enable APIs and Services > Search for "Google People" > Enable```

### 1.1.3. Create Credentials

```Overview > Create Credentials```

Get Fingerprint (SHA-1):

```bash
keytool -importkeystore \
        -srckeystore ~/.android/debug.keystore \
        -destkeystore ~/.android/debug.keystore \
        -deststoretype pkcs12

keytool -keystore ~/.android/debug.keystore -list -v
```

Set Package Name:

```com.domain.skeleton```

# 1.2. Facebook SDK

### 1.2.1. Create Project

```https://developers.facebook.com/apps > Add New Project```

### 1.2.2. Enable API

```Login do Facebook > Inicio Rapido > Android```
 
Package Name:

```com.domain.skeleton```

Class:

```com.domain.skeleton.auth.activity.LoginActivity```

### 1.2.3. Generate Hash Key

Debug:

```keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64```

Release:

```keytool -exportcert -alias YOUR_RELEASE_KEY_ALIAS -keystore YOUR_RELEASE_KEY_PATH | openssl sha1 -binary | openssl base64```

Paste Hash Key:

```/core/res/values/system.xml```

```xml
<string name="api_facebook_app_id">APP_ID</string>
<string name="api_facebook_login_protocol_scheme">LOGIN_PROTOCOL_SCHEME</string>
```

# 2. Configure Local Server

### 2.1. Clone Project

```bash
git clone https://github.com/junioregis/skeleton-rails.git
```

### 2.2. Follow the steps from the Wiki

```https://github.com/junioregis/skeleton-rails/wiki```

Start the local server.

# 3. Run Android App
