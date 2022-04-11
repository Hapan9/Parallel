#!/bin/bash
mpiexec -n 3 bin/Debug/net5.0/Lab6 2000
mpiexec -n 5 bin/Debug/net5.0/Lab6 2000
mpiexec -n 9 bin/Debug/net5.0/Lab6 2000
mpiexec -n 17 bin/Debug/net5.0/Lab6 2000
mpiexec -n 41 bin/Debug/net5.0/Lab6 2000

mpiexec -n 9 bin/Debug/net5.0/Lab6 1000
mpiexec -n 9 bin/Debug/net5.0/Lab6 1504
mpiexec -n 9 bin/Debug/net5.0/Lab6 2000
mpiexec -n 9 bin/Debug/net5.0/Lab6 2504