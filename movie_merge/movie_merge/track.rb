require 'simple_time'

class Track
  attr_accessor :duration, :title

  def initialize(duration, title)
  	@duration = duration
  	@title = title
  end

  def self.parse(str)
  	results = str.split(",")
  	duration_str = results[0]
  	title_str = results[1]
  	Track.new(SimpleTime.parse(duration_str), title_str)
  end

end
