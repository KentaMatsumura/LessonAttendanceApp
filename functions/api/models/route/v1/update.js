exports.updateLesson = (lesson, docId) => {

    const admin = require('firebase-admin');
    const functions = require('firebase-functions');
    admin.initializeApp(functions.config().firebase);
    let db = admin.firestore();

    try {
        let aTuringRef = db.collection('lessons').doc(docId);

        let updateSingle = aTuringRef.update({attendanceState: lesson['attendanceState']});
        console.log("updated")
    } catch (e) {
        console.log(e);
    }

    return 0;
};