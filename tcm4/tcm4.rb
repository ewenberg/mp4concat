require './movie_merge/movie_merge.rb'

merge = MovieMerge.new("/Users/erik/temp/tcm4/", "tcm4_track_listing.txt")
merge.load
merge.show_chapters
merge.show_command