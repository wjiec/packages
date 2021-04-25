package xcmd

import (
	"bulk/pkg/utils"
	"bulk/pkg/utils/refls"
	"reflect"
	"strconv"
	"unsafe"

	"github.com/spf13/cobra"
)

type Commander struct {
	*cobra.Command
}

func Bind(cmd *cobra.Command, v interface{}) error {
	return refls.VisitFields(v, func(v reflect.Value, t *reflect.StructField) error {
		switch reflect.Indirect(v).Kind() {
		case reflect.Bool:
			def, _ := strconv.ParseBool(t.Tag.Get("default"))
			if shorthand := t.Tag.Get("shorthand"); shorthand != "" {
				cmd.Flags().BoolVarP((*bool)(unsafe.Pointer(v.Addr().Pointer())),
					utils.CamelCaseToHyphen(t.Name), shorthand, def, t.Tag.Get("usage"))
			} else {
				cmd.Flags().BoolVar((*bool)(unsafe.Pointer(v.Addr().Pointer())),
					utils.CamelCaseToHyphen(t.Name), def, t.Tag.Get("usage"))
			}
		}

		return nil
	})
}
