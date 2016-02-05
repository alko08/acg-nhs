import os,sys


def finalizing(file_to_commit):
    message = raw_input("Please enter a commit message: ")
    print "Committing: %r" % file_to_commit
    print "Message: %r" % message
    final = raw_input("Press enter to commit, type abort to abort")
    if final == "":
        commit_cmd = 'git commit -m "%s"' % message
        os.system("commit_cmd")
    else:
        sys.exit(0)
    

def commit():
    file_to_commit = raw_input("Type the name of the file to commit (enter nothing for rocketBot.java, for anything else type the path after opmodes\): ")
    if file_to_commit == "":
        os.system("git add gaia\FtcRobotController\src\main\java\com\qualcomm\ftcrobotcontroller\opmodes\ftcdefault\rocketBot.java")
    else:
        os.system("git add gaia\FtcRobotController\src\main\java\com\qualcomm\ftcrobotcontroller\opmodes\%s ") % file_to_commit
        finalizing(file_to_commit)

def init():
    os.system("git branch")
    print "Please confirm you are on the corrent branch, type abort to stop now, or press enter to continue"
    abort = raw_input("\n>")
    if abort == "":
        commit()
    else:
        sys.exit(0)
init()