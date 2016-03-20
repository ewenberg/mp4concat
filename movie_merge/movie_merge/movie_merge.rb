require 'ffprobe'
require 'simple_time'
require 'csv'

class MovieMerge
	attr_accessor :chapter_durations
	attr_accessor :chapter_start_times
	attr_accessor :input
	attr_accessor :base_path
	attr_accessor :input_file

	def initialize(base_path, input_file)
		@input = []
		@chapter_start_times = []
		@chapter_durations = []
		@base_path = base_path
		@input_file = input_file
		if !File.exist?(@input_file) then
		  create_input_file
		end
	end

  def create_input_file
    File.open(@input_file, "w") do |out|
      out.puts "filename|chapter_name"
      Dir.entries(@base_path).sort.select {|f| !File.directory? f}.each do |file_name|
        out.puts file_name + "|" + file_name
      end
    end
  end
  
	def load
		running_duration = SimpleTime.from_seconds("0")
		@input = CSV.read(@input_file, :col_sep => "|", :headers => true)
		@input.each do |in_row|
		  puts "Reading " + @base_path + in_row["filename"]
			duration = FFProbe.duration(@base_path + in_row["filename"].gsub(" ", "\\ "))
			@chapter_durations << duration
			@chapter_start_times << running_duration
			running_duration = running_duration + duration
	    end
	end

	def show_chapters
		@chapter_start_times.each_with_index do |time, index|
			puts "#{@chapter_start_times[index]},#{@input[index]['chapter_name']}"
	 	end
	end

	def show_command
		# ffmpeg -i lesson-01-web3.mp4 -i lesson-02-web3.mp4 -i lesson-03-web3.mp4 
		#  -i lesson-04-web3.mp4 -i lesson-05-web2.mp4 
		#  -filter_complex '[0:0][0:1][1:0][1:1][2:0][2:1][3:0][3:1][4:0][4:1]concat=n=5:v=1:a=1[v][a]' 
		#  -map [v] -map [a] -strict -2 test.mp4
		file_names = ""
		filters = ""
		@input.each_with_index do |in_row, idx|
			#file_names << "-i #{input[idx]['filename']} "
			file_names << "-i " + input[idx]['filename'].gsub(" ", "\\ ").gsub("(", "\\(").gsub(")", "\\)") + " "
			filters << "[#{idx}:0][#{idx}:1]"
		end
		puts "ffmpeg #{file_names} -filter_complex '#{filters}concat=n=#{@input.size}:v=1:a=1[v][a]' -map [v] -map [a] -strict -2 new_movie.mp4"
	end
end
