package main

import (
	"fmt"
	"math/rand"
	"strconv"
	"time"
)


type Barrier struct {
	i int
	i_max int
}

func (s *Barrier) Await() {
	s.i++
	for s.isAcquired() {
		time.Sleep(time.Millisecond)
	}
	time.Sleep(time.Millisecond * 100)	
	fmt.Println("Barrier done")
}

func (s *Barrier) Reset() {
	s.i = 0
}

func (s *Barrier) isAcquired() bool {
	return s.i < s.i_max
}


func ArrayManager( barrier *Barrier, array *[3][6]int, i int) {
	
	rand.Seed(time.Now().UnixNano())
	fmt.Println("Array manager " + strconv.Itoa(i) + " started")
	if CheckSum(array) {
		fmt.Println("Array manager " + strconv.Itoa(i) + " stopped")	
		barrier.Await()
	}	
	for barrier.isAcquired(){		
		time.Sleep(time.Millisecond*100)
		PrintArray(*array, i)
		if (Sum(array[i]) < (Sum(array[(i+1)%3]) + Sum(array[(i+2)%3]))/2) || Sum(array[i]) < Sum(array[(i+1)%3]) || Sum(array[i]) < Sum(array[(i+2)%3]) {
			rand_num := rand.Intn(len(array[i]))	
			array[i][rand_num] ++
		} else {
			rand_num := rand.Intn(len(array[i]))
			array[i][rand_num] --
		}
		if CheckSum(array) {
			fmt.Println("Array manager " + strconv.Itoa(i) + " stopped")	
			barrier.Await()
			break
		}		
	}
}

func PrintArray(array [3][6]int, i int) {
	fmt.Print(strconv.Itoa(i) + ": ")
	for _, v := range array[i] {
		fmt.Print(strconv.Itoa(v) + " ")
	}
	fmt.Print(" - " + strconv.Itoa(Sum(array[i])))
	fmt.Println()
}

func CheckSum( array *[3][6]int) bool {
	fmt.Println( Sum(array[0]), Sum(array[1]), Sum(array[2]))	
	return Sum(array[0]) == Sum(array[1]) && Sum(array[1]) == Sum(array[2])
}

func Sum(a [6]int) int {
	sum := 0
	for _, v := range a {
		sum += v
	}
	return sum
}



func main() {

	barrier := Barrier{i: 0, i_max: 3}
	array := [3][6]int{  
		{0,5,3,2,4,6},
		{0,1,8,2,6,6},
		{1,4,7,5,8,4},
	   }

	go ArrayManager(&barrier, &array, 0)	
	time.Sleep(time.Millisecond*50)
	go ArrayManager(&barrier, &array, 1)
	time.Sleep(time.Millisecond*50)
	go ArrayManager(&barrier, &array, 2)

	finished := make(chan bool)
	<- finished

}