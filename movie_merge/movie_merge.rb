$:.unshift File.dirname(__FILE__)
$:.unshift File.join(File.dirname(__FILE__),"movie_merge")

require "simple_time"
require "track"
require "ffprobe"
require "movie_merge"