var webPage = require('webpage');
var page = webPage.create();

page.viewportSize = { width: 800, height: 968 };
page.open('$path', function start(status) {
    page.render('$imgName', {format: 'png', quality: '100'});
    phantom.exit();
});