require 'minitest/autorun'
require './movie_merge.rb'

describe Track do

  it "#parse" do
  	result = Track.parse("01:02,Track 1")
  	result.duration.must_equal SimpleTime.new(0, 1, 2)
  	result.title.must_equal "Track 1"
  end

end