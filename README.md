Instructions for merging movie files

First put all the files to be merged in one directory.  Then use 'ls > <outputfile>' or something similar to
create a listing of the files to be merged.  Make sure they are in the correct order.  If you want to
use the filenames for chapter names, you are done.  Otherwise, create another file with the chapter
names, in the same order as the file names.

Now you can run merge_filenames_and_titles.rb to create a single file with both file names and chapter names.
Call this file chapters.txt, or something similar.

You can now create a ruby file to run MovieMerge.  MovieMerge takes 2 parameters, the name of the directory
where the files are located, and the name of the chapters.txt file.  With these inputs, MovieMerge will
spit out a *bunch* of output.  This output can be copy/pasted onto the command line to run the 
actual ffmpeg command that will merge the movie files into a single mp4.

There is also other output that will provide the subler file for chapter markers.  Save this output
to another file called subler.txt or something similar.  Once the mp4 is created,
load it into subler and from the Finder drag the subler.txt file directly onto the subler app.  Subler
will use this to create chapter markers.  Save the file and then you are ready to load into iTunes.


