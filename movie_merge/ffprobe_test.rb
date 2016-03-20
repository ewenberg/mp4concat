require 'minitest/autorun'
require './movie_merge.rb'

describe FFProbe do

  it "#duration" do
  	FFProbe.duration("./test.mp4").must_equal SimpleTime.from_seconds("261.04")
  end

end