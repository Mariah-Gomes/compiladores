package main
import "fmt"
import "sort"
func main() {
fmt.Println(" ")
sort.Ints([]int{1, 2, 3})
fmt.Println("Digite algo: ")
var t string
fmt.Scanln(&t)
fmt.Println(t)
}
