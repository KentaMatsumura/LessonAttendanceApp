const express = require('express');
const router = express.Router();
const db = require('../../firebase');

// Read an Item
router.get('/', async (req, res, next) => {
    try {
        let docRef = await db.collection('lessons')
            .doc('jojo.k.bump2526@gmail.com_1_1');
        let docs = docRef.get()
        // eslint-disable-next-line promise/always-return
            .then(doc => {
                res.json(doc.data());
                console.log("item get");
            });

    } catch (e) {
        // eslint-disable-next-line callback-return
        next(e);
    }
});

module.exports = router;