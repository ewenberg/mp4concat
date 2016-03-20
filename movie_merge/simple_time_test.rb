require 'minitest/autorun'
require './movie_merge.rb'

describe SimpleTime do

  it "defaults to 00:00:00 format" do
  	SimpleTime.new(0, 0, 0).to_s.must_equal "00:00:00.000"
  end
  
  describe "#parse" do
    it "parses strings with hours" do
  	 st = SimpleTime.parse("01:02:15")
  	 st.hours.must_equal 1
  	 st.minutes.must_equal 2
  	 st.seconds.must_equal 15
    end

    it "parses strings with only minutes and seconds" do
      st = SimpleTime.parse("04:16")
      st.hours.must_equal 0
      st.minutes.must_equal 4
      st.seconds.must_equal 16
    end
  end

  it "has equals" do
  	st123 = SimpleTime.new(1, 2, 3, 4)
  	also123 = SimpleTime.new(1, 2, 3, 4)
  	st124 = SimpleTime.new(1, 2, 4)

  	st123.must_equal also123
  end

  it "adds seconds" do
  	st1 = SimpleTime.new(0, 0, 50)
  	st2 = SimpleTime.new(0, 0, 11)
    st3 = SimpleTime.new(0, 1, 1)

    (st1 + st2).must_equal st3
  end

  it "adds minutes" do
    st1 = SimpleTime.new(0, 30, 0)
    st2 = SimpleTime.new(0, 10, 0)
    st3 = SimpleTime.new(0, 40, 0)

    (st1 + st2).must_equal st3
  end

  it "adds milliseconds" do
    st1 = SimpleTime.new(0, 0, 1, 2)
    st2 = SimpleTime.new(0, 0, 2, 3)
    st3 = SimpleTime.new(0, 0, 3, 5)
    (st1 + st2).must_equal st3
  end

  it "normalizes seconds" do
    SimpleTime.new(0, 0, 61).must_equal SimpleTime.new(0, 1, 1)
  end

  it "normalizes minutes" do
    SimpleTime.new(0, 85, 10).must_equal SimpleTime.new(1, 25, 10)
  end

  it "normalizes milliseconds" do
    SimpleTime.new(0, 0, 0, 1001).must_equal SimpleTime.new(0, 0, 1, 1)
  end

  it "accepts 0 hours" do
    SimpleTime.new(0, 10, 20).to_s.must_equal "00:10:20.000"
  end

  it "accepts milliseconds" do
    SimpleTime.new(0, 1, 1, 250).to_s.must_equal "00:01:01.250"
  end

  it "from_seconds" do
    SimpleTime.from_seconds(60).must_equal SimpleTime.new(0, 1, 0)
    SimpleTime.from_seconds(120).must_equal SimpleTime.new(0,2,0)

    SimpleTime.from_seconds(3601).must_equal SimpleTime.new(1, 0, 1)
    SimpleTime.from_seconds(3661).must_equal SimpleTime.new(1, 1, 1)

    SimpleTime.from_seconds(45.351).must_equal SimpleTime.new(0, 0, 45, 351)
    SimpleTime.from_seconds("23.129").must_equal SimpleTime.new(0, 0, 23, 129)
  end
end
