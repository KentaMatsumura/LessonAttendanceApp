const express = require('express');
const router = express.Router();
const db = require('../../firebase');


router.get('/', async (req, res, next) => {
    try {
        const itemSnapshot = await db.collection('lessons').get();
        const items = [];
        itemSnapshot.forEach(doc => {
            items.push({
                id: doc.id,
                data: doc.data()
            });
        });
        res.json(items);
    } catch (e) {
        // eslint-disable-next-line callback-return
        next(e);
    }
});

module.exports = router;