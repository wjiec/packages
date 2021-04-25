package refls

import (
	"reflect"
)

// Visit visits the struct fields in order, calling fn for each
func VisitFields(v interface{}, fn func(reflect.Value, *reflect.StructField) error) error {
	rv := reflect.Indirect(reflect.ValueOf(v))
	if rv.Kind() == reflect.Struct {
		rt := rv.Type()
		for i := 0; i < rv.NumField(); i++ {
			ft := rt.Field(i)
			if err := fn(rv.Field(i), &ft); err != nil {
				return err
			}
		}

		return nil
	}

	return nil
}
