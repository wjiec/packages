package main

import (
	"fmt"
	"os"
	"regexp"
	"strings"

	"github.com/howeyc/gopass"
)

var (
	DigitRegex = regexp.MustCompile(`^\d+$`)
)

func Input(prompt string, a ...interface{}) error {
	fmt.Print(prompt)
	_, err := fmt.Scan(a...)
	return err
}

func HasDuplicateDigit(bs []byte) bool {
	var unique = make(map[byte]struct{})
	for _, char := range bs {
		if _, ok := unique[char]; ok {
			return true
		}
		unique[char] = struct{}{}
	}
	return false
}

func main() {
	var password []byte
	var err error
	for {
		if password, err = gopass.GetPasswdPrompt("请输入密码: ", false, os.Stdin, os.Stdout); err == nil {
			if DigitRegex.Match(password) {
				if !HasDuplicateDigit(password) {
					break
				}
			}
		}
		fmt.Println("输入密码错误, 请重新输入")
	}

	fmt.Printf("密码已输入完成, %d位数字, 按q查看密码并退出\n", len(password))
	var charset = make(map[byte]int)
	for index, value := range password {
		charset[value] = index
	}

	for {
		var guess string
		if err := Input("请猜一个数: ", &guess); err != nil {
			fmt.Println("输入错误, 请重新输入")
			continue
		}

		if guess == string(password) {
			_ = Input(fmt.Sprintf("\n恭喜你猜对了, 答案是%s\n", password), &password)
			os.Exit(0)
		} else if strings.ToLower(guess) == "q" {
			_ = Input(fmt.Sprintf("\n你没答出来, 答案是%s\n", password), &password)
			os.Exit(0)
		}

		var a, b int
		for index, value := range []byte(guess) {
			if password[index] == value {
				a += 1
			} else if _, ok := charset[value]; ok {
				b += 1
			}
		}
		fmt.Printf("\n%dA%dB\n", a, b)
	}
}
