#Main Needs#

test and make more robust
make a better clock icon

#Fix#


why are the by day columns not centering to grid

make it so by year shows all 12 months

#Process Monitor#

add process watcher for automated task recording!

set an idle threshold for cpu and memory usage to determine if a process is active
get a list of all processes and their cpu and memory usage
allow a user to pick a process as a task and instruct the program to monitor that task for activity in egard to the specified idle threshhold	

when in process monitor mode, frequently check the activity of the process. If the process is active, start a timer or record the start time. Monitor the process and stop the timer or record the end time when the rpocess is no longer active. Once the start and end times exist, record that sequence as a session.

Use powershell.exe while(1) {ps netbeans64} to continually list the data for the process