# LessonAttendanceApp

## 概要  
***フロントエンド***
- 時間割が出力される(must)
- 授業詳細が出力される(must)
- 授業の座標を取得する(must)
- 授業情報を取得する(want1)
- Android端末で位置情報をバックグラウンドで取得(want2)  

***バックエンド***
- 座標をもとに出席判定を行う(must)
- ログイン機能の強化(want3)
- 授業開始前にプッシュ通知(want4)
- 正常に出席状況をDBへ保存できたことをAndroid側に通知(want5)
    
## 開発環境
***フロントエンド***
- Android Studio 3.5.2
- java 12.0.1
- kotlin 1.3.50

***バックエンド***
- PhpStorm 2019.2.4
- node.js v8.15.0
- デプロイ先: Google Cloud Functions


## DB環境
<img src="./readme_images/DB_relation.png" width="75%">   



## アプリケーションの動き
<img src="./readme_images/display_flow.png" width="100%">   


## 実装終了



## 今後の予定
***フロントエンド***
- 授業情報を取得する(want1)
    - webで公開されている大学のシラバス情報をAPI化することでAndroid側から取得する
- Android端末で位置情報をバックグラウンドで取得(want2)  
    - 現在はボタンを押して位置情報を取得しているため、アプリを開かずバックグラウンドで位置情報を取得可能にする

***バックエンド***
- ログイン機能の強化(want3)
    - 各大学ごとの学生用メールアドレスを用いた認証を行う
- 授業開始前にプッシュ通知(want4)
    - [Cloud Messaging for Firebase](https://firebase.google.com/docs/cloud-messaging?hl=ja)
    を用い、授業開始前にAndroidへプッシュ通知を送る
- 正常に出席状況をDBへ保存できたことをAndroid側に通知(want5)
    - 出席判定も含めて正常にバックエンド側が機能していることをAndroidへ通知する