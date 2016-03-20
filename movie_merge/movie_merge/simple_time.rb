class SimpleTime
  attr_reader :hours, :minutes, :seconds, :milliseconds

  def initialize(hours, minutes, seconds, milliseconds = 0)
    initial_seconds = milliseconds / 1000
    initial_minutes = seconds / 60
    initial_hours = minutes / 60

    @hours = hours + initial_hours
    @minutes = (minutes % 60) + initial_minutes
    @seconds = seconds % 60 + initial_seconds
    @milliseconds = milliseconds % 1000
  end

  def to_s
  	@hours.to_s.rjust(2, '0') + ":" +
  	@minutes.to_s.rjust(2, '0') + ":" +
  	@seconds.to_s.rjust(2, '0') + "." +
    @milliseconds.to_s.rjust(3, '0')
  end

  def self.parse(str)
  	parts = str.split(":")

    if parts.size == 3
  	 hours = parts[0].to_i
  	 minutes = parts[1].to_i
  	 seconds = parts[2].to_i
    elsif parts.size == 2
      hours = 0
      minutes = parts[0].to_i
      seconds = parts[1].to_i
    else
      raise "Unrecognized format"
    end
    
  	SimpleTime.new(hours, minutes, seconds)
  end

  def self.from_seconds(in_seconds)
    whole = in_seconds.to_f.truncate
    milliseconds = ((in_seconds.to_f - whole).round(3) * 1000).truncate

    hours = whole / 3600
    minutes = (whole - (3600 * hours)) / 60
    seconds = whole % 60
    SimpleTime.new(hours, minutes, seconds, milliseconds)
  end

  def ==(other)
    if other.nil?
      return false
    elsif !other.instance_of? SimpleTime
      return false
    else
      return (other.hours == self.hours) && (other.minutes == self.minutes) && (other.seconds == self.seconds) && (other.milliseconds == self.milliseconds)
    end
  end

  def +(other)
    if !other.instance_of? SimpleTime
      raise "Cannot add other object types to a SimpleTime"
    else
      return SimpleTime.new(self.hours + other.hours, self.minutes + other.minutes, self.seconds + other.seconds, self.milliseconds + other.milliseconds)
    end
  end
end

