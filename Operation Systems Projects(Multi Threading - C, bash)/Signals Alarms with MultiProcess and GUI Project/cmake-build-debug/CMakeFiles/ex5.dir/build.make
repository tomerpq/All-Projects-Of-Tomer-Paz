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
CMAKE_SOURCE_DIR = /home/cs/CLionProjects/ex5

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/cs/CLionProjects/ex5/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/ex5.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/ex5.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/ex5.dir/flags.make

CMakeFiles/ex5.dir/ex51.c.o: CMakeFiles/ex5.dir/flags.make
CMakeFiles/ex5.dir/ex51.c.o: ../ex51.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/cs/CLionProjects/ex5/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/ex5.dir/ex51.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/ex5.dir/ex51.c.o   -c /home/cs/CLionProjects/ex5/ex51.c

CMakeFiles/ex5.dir/ex51.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/ex5.dir/ex51.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/cs/CLionProjects/ex5/ex51.c > CMakeFiles/ex5.dir/ex51.c.i

CMakeFiles/ex5.dir/ex51.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/ex5.dir/ex51.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/cs/CLionProjects/ex5/ex51.c -o CMakeFiles/ex5.dir/ex51.c.s

CMakeFiles/ex5.dir/ex52.c.o: CMakeFiles/ex5.dir/flags.make
CMakeFiles/ex5.dir/ex52.c.o: ../ex52.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/cs/CLionProjects/ex5/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/ex5.dir/ex52.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/ex5.dir/ex52.c.o   -c /home/cs/CLionProjects/ex5/ex52.c

CMakeFiles/ex5.dir/ex52.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/ex5.dir/ex52.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/cs/CLionProjects/ex5/ex52.c > CMakeFiles/ex5.dir/ex52.c.i

CMakeFiles/ex5.dir/ex52.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/ex5.dir/ex52.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/cs/CLionProjects/ex5/ex52.c -o CMakeFiles/ex5.dir/ex52.c.s

# Object files for target ex5
ex5_OBJECTS = \
"CMakeFiles/ex5.dir/ex51.c.o" \
"CMakeFiles/ex5.dir/ex52.c.o"

# External object files for target ex5
ex5_EXTERNAL_OBJECTS =

ex5: CMakeFiles/ex5.dir/ex51.c.o
ex5: CMakeFiles/ex5.dir/ex52.c.o
ex5: CMakeFiles/ex5.dir/build.make
ex5: CMakeFiles/ex5.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/cs/CLionProjects/ex5/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking C executable ex5"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/ex5.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/ex5.dir/build: ex5

.PHONY : CMakeFiles/ex5.dir/build

CMakeFiles/ex5.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/ex5.dir/cmake_clean.cmake
.PHONY : CMakeFiles/ex5.dir/clean

CMakeFiles/ex5.dir/depend:
	cd /home/cs/CLionProjects/ex5/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/cs/CLionProjects/ex5 /home/cs/CLionProjects/ex5 /home/cs/CLionProjects/ex5/cmake-build-debug /home/cs/CLionProjects/ex5/cmake-build-debug /home/cs/CLionProjects/ex5/cmake-build-debug/CMakeFiles/ex5.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/ex5.dir/depend

