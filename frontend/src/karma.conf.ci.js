// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: '../',
    frameworks: [
      'jasmine',
      '@angular-devkit/build-angular',
    ],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-junit-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular-devkit/build-angular/plugins/karma'),
    ],
    reporters: [
      'progress',
      'junit',
    ],
    port: 9876,
    colors: false,
    logLevel: config.LOG_INFO,
    autoWatch: false,
    singleRun: true,
    restartOnFileChange: false,
    browsers: [
      'ChromeCi',
    ],
    browserDisconnectTimeout: 6000, // our ci server is too weak for lower values :(
    client: {
      clearContext: false, // leave Jasmine Spec Runner output visible in browser
    },
    junitReporter: {
      outputDir: 'target/junit-reports',
      outputFile: 'test-results.xml',
      useBrowserName: true,
    },
    coverageIstanbulReporter: {
      dir: require('path').relative(__dirname, 'frontend/target/coverage'),
      reports: [
        'text',
        'cobertura',
      ],
      fixWebpackSourcePaths: true,
    },
    customLaunchers: {
      'ChromeCi': {
        base: 'Chrome',
        flags: [
          '--headless',
          '--disable-gpu',
          '--no-sandbox',
          '--remote-debugging-port=9222',
        ],
      },
    },
  });
};
