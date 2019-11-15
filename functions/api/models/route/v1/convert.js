exports.string2JaDate = (st) => {
    return new Date(st);
};

exports.timestamp2Date = (ts) => {
    return ts ? new Date(ts * 1000) : new Date();
};

exports.thisWeekDate = (st, et, now) => {
    let i = 0;
    while (i < 16) {
        if (st.getTime() - now.getTime() > 0) {
            st.setDate(st.getDate() - 7);
            et.setDate(et.getDate() + ((i - 1) * 7));
            return {
                startDateNow: st,
                endDateNow: et,
                lessonCount: i
            }
        } else {
            st.setDate(st.getDate() + 7);
            i++;
        }
    }
    return {
        startDateNow: null,
        endDateNow: null,
        lessonCount: null
    };

};
