const functions = require('firebase-functions');

functions.region('asia-northeast1');

const judgeLocation = require('./api/models/route/v1/judgelocation');

exports.judgeLessonAttendance = functions.firestore
    .document('lessons/{lessonId}')
    .onWrite((change, context) => {
        const lesson = change.after.data();
        judgeLocation.judge(lesson, context);
        return 0
    });