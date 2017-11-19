var gulp = require('gulp');
var useref = require('gulp-useref');
var uglify = require('gulp-uglify');
gutil = require('gulp-util');

gulp.task('hello', function(){
	// var assets  = useref.assets();

	return gulp.src('index.html')
	.pipe(uglify())
	.on('error', function (err) { gutil.log(gutil.colors.red('[Error]'), err.toString()); })
	.pipe(useref())
	.pipe(gulp.dest('dist'))
});