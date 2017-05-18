var path = require('path');
var express = require('express');
var webpack = require('webpack');
var config = require('./webpack.config.dev');
var spawn = require('child_process').spawn;
var spawnSync = require('child_process').spawnSync;
var app = express();
var compiler = webpack(config);

app.use(require('webpack-dev-middleware')(compiler, {
  noInfo: true,
  publicPath: config.output.publicPath
}));

app.use(require('webpack-hot-middleware')(compiler));

app.use('/public', express.static('public'));

app.get('*', function(req, res) {
  res.sendFile(path.join(__dirname, 'index.html'));
});

var lsof = spawn('lsof', ['-i', ':3000']);
lsof.stdout.on('data', function(data){
  console.log('data ' + data);
  if(data.length > 0 ){
    console.log('data ' + data.length);
    var pkill = spawn('pkill', ['-f', 'node']);
    pkill.stdout.on('data', function (data) {
         console.log(`child process exited with code ${data}`);
    });
    pkill.on('close', (code) => {
      console.log(`child process exited with code ${code}`);
    });
  }
  app.listen(3000, function(er) {
    if (er) {
      console.log(er);
      return;
    }
    console.log('Listening at http://localhost:3000');
  });
});

/*, function(err, stdout, stderr) {
    if(stdout != null && stdout.length > 0 ){
      console.log('stdout ' + stdout.length);
      exec('pkill node', function(error, stdout, stderr) {
        console.log('stdout ' + stdout.length);
        console.log('stdout ' + stderr.length);
        if (error != null) {
            console.log('exec error: ' + err);
            return;
        }else{
          console.log('exec error: ' + stdout);
        }
      });
    }

    if(err != null){
        console.log('error: ' + err.code);
        console.log(err.stack);
        return;
    }
    app.listen(3000, function(er) {
      if (er) {
        console.log(er);
        return;
      }
      console.log('Listening at http://localhost:3000');
    });
});
*/
