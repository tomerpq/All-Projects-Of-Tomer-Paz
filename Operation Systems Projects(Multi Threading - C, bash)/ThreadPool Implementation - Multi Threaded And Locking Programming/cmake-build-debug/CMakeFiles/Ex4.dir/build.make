# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.14

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /snap/clion/70/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /snap/clion/70/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/cs/CLionProjects/Ex4

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/cs/CLionProjects/Ex4/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Ex4.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Ex4.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Ex4.dir/flags.make

CMakeFiles/Ex4.dir/main.c.o: CMakeFiles/Ex4.dir/flags.make
CMakeFiles/Ex4.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/cs/CLionProjects/Ex4/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Ex4.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Ex4.dir/main.c.o   -c /home/cs/CLionProjects/Ex4/main.c

CMakeFiles/Ex4.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Ex4.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/cs/CLionProjects/Ex4/main.c > CMakeFiles/Ex4.dir/main.c.i

CMakeFiles/Ex4.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Ex4.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/cs/CLionProjects/Ex4/main.c -o CMakeFiles/Ex4.dir/main.c.s

CMakeFiles/Ex4.dir/osqueue.c.o: CMakeFiles/Ex4.dir/flags.make
CMakeFiles/Ex4.dir/osqueue.c.o: ../osqueue.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/cs/CLionProjects/Ex4/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/Ex4.dir/osqueue.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Ex4.dir/osqueue.c.o   -c /home/cs/CLionProjects/Ex4/osqueue.c

CMakeFiles/Ex4.dir/osqueue.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Ex4.dir/osqueue.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/cs/CLionProjects/Ex4/osqueue.c > CMakeFiles/Ex4.dir/osqueue.c.i

CMakeFiles/Ex4.dir/osqueue.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Ex4.dir/osqueue.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/cs/CLionProjects/Ex4/osqueue.c -o CMakeFiles/Ex4.dir/osqueue.c.s

CMakeFiles/Ex4.dir/threadPool.c.o: CMakeFiles/Ex4.dir/flags.make
CMakeFiles/Ex4.dir/threadPool.c.o: ../threadPool.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/cs/CLionProjects/Ex4/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object CMakeFiles/Ex4.dir/threadPool.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Ex4.dir/threadPool.c.o   -c /home/cs/CLionProjects/Ex4/threadPool.c

CMakeFiles/Ex4.dir/threadPool.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Ex4.dir/threadPool.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/cs/CLionProjects/Ex4/threadPool.c > CMakeFiles/Ex4.dir/threadPool.c.i

CMakeFiles/Ex4.dir/threadPool.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Ex4.dir/threadPool.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/cs/CLionProjects/Ex4/threadPool.c -o CMakeFiles/Ex4.dir/threadPool.c.s

# Object files for target Ex4
Ex4_OBJECTS = \
"CMakeFiles/Ex4.dir/main.c.o" \
"CMakeFiles/Ex4.dir/osqueue.c.o" \
"CMakeFiles/Ex4.dir/threadPool.c.o"

# External object files for target Ex4
Ex4_EXTERNAL_OBJECTS =

Ex4: CMakeFiles/Ex4.dir/main.c.o
Ex4: CMakeFiles/Ex4.dir/osqueue.c.o
Ex4: CMakeFiles/Ex4.dir/threadPool.c.o
Ex4: CMakeFiles/Ex4.dir/build.make
Ex4: CMakeFiles/Ex4.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/cs/CLionProjects/Ex4/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Linking C executable Ex4"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Ex4.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Ex4.dir/build: Ex4

.PHONY : CMakeFiles/Ex4.dir/build

CMakeFiles/Ex4.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Ex4.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Ex4.dir/clean

CMakeFiles/Ex4.dir/depend:
	cd /home/cs/CLionProjects/Ex4/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/cs/CLionProjects/Ex4 /home/cs/CLionProjects/Ex4 /home/cs/CLionProjects/Ex4/cmake-build-debug /home/cs/CLionProjects/Ex4/cmake-build-debug /home/cs/CLionProjects/Ex4/cmake-build-debug/CMakeFiles/Ex4.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Ex4.dir/depend

