const proxy = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        proxy('/api', {
            target: process.env.REACT_APP_BASE_URL, // 비즈니스 서버 URL 설정
            changeOrigin: true
        })
    );
};