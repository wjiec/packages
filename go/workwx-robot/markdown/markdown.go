package markdown

type Segment interface {
	String() string

	private()
}

//type inline struct {
//	val string
//}
//
//// String returns inline value
//func (i *inline) String() string {
//	return i.val
//}
//
//// private interface implement
//func (*inline) private() {}
//
//type Title string
//
//// Level create a leveled title
//// level range from 1 and 6 and are automatically set to 1 or 6 when out of range
//func (t Title) Level(level int) Segment {
//	return &inline{val: fmt.Sprintf("%s %s", strings.Repeat("#", int(math.Min(float64(level), 6))), t)}
//}
