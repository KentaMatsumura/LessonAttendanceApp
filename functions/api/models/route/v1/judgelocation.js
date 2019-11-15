exports.judge = (lesson, context) => {
    const convert = require('./convert');
    const location = require('./location');
    const ud = require('./update');

    const japanNowDate = convert.string2JaDate(context.timestamp);
    const startDate = convert.timestamp2Date(lesson['startTime']._seconds);
    const endDate = convert.timestamp2Date(lesson['endTime']._seconds);
    let {startDateNow, endDateNow, lessonCount} = convert.thisWeekDate(startDate, endDate, japanNowDate);
    if ((japanNowDate.getTime() - startDate.getTime() > 0) && (japanNowDate.getTime() - endDate.getTime() < 0)) {
        const judge = location.compareLocation(lesson['presentLocation'], lesson['coordinate']);
        if (judge) {
            if (lesson['attendanceState'].length === lessonCount) {
                console.log("今日の授業は出席登録済み");
            } else if (lesson['attendanceState'].length > lessonCount) {
                console.log("要素が多いので削除する");
                while (lesson['attendanceState'].length > lessonCount) {
                    lesson['attendanceState'].pop()
                }
            } else {
                while (lesson['attendanceState'].length < lessonCount - 1) {
                    console.log("要素が少ないので欠席登録");
                    lesson['attendanceState'].push(0)
                }
                lesson['attendanceState'].push(1)
            }
            console.log(lesson);
            ud.updateLesson(lesson, context['params']['lessonId'])
        } else {
            console.log('場所が遠すぎます!!!')
        }

    } else {
        console.log('!!!incorrect time!!!')
    }
    return 0
};