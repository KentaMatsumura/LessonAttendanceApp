exports.compareLocation = (now, target) => {
    let attendanceFlag = false;

    function dis(lat1, lng1, lat2, lng2) {
        lat1 *= Math.PI / 180;
        lng1 *= Math.PI / 180;
        lat2 *= Math.PI / 180;
        lng2 *= Math.PI / 180;
        return 6371 * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1) + Math.sin(lat1) * Math.sin(lat2));
    }

    const diff_m = dis(
        Number(now['_latitude']),
        Number(now['_longitude']),
        Number(target['_latitude']),
        Number(target['_longitude'])) * 1.60934;

    attendanceFlag = diff_m <= 30;

    return attendanceFlag;
};