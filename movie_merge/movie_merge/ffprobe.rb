# ffmpeg -i lesson-01-web3.mp4 -i lesson-02-web3.mp4 -i lesson-03-web3.mp4 
#  -i lesson-04-web3.mp4 -i lesson-05-web2.mp4 
#  -filter_complex '[0:0][0:1][1:0][1:1][2:0][2:1][3:0][3:1][4:0][4:1]concat=n=5:v=1:a=1[v][a]' 
#  -map [v] -map [a] -strict -2 test.mp4

require 'open3'
require 'simple_time.rb'

class FFProbe

  DURATION_REGEX = /duration=(.*$)/

  def self.duration(filename)
    result = []
    duration = SimpleTime.from_seconds("0")
  	Open3.popen2e("ffprobe -show_format #{filename}") do |i, o, t|
  		result = o.readlines
  	end

    result.each do |line|
      match = line.scan(DURATION_REGEX)
      if match.length > 0
        duration = SimpleTime.from_seconds(match[0][0])
      end
    end
  	duration
  end

end
