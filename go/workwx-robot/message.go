package workrobot

import (
	"encoding/json"
	"errors"
)

var (
	// ErrMessageTooLong represents the message length exceeds the limit
	ErrMessageTooLong = errors.New("message too long")
)

const (
	TextMessageMaxLength     = 2048
	MarkdownMessageMaxLength = 4096
)

// Messager represents a message will sent
type Messager interface {
	Message() []byte
}

// Mention represents a mention message
type Mention struct {
	members []string
	mobiles []string

	all bool
}

// MentionAll make mentioned all group members
func (msg *Mention) MentionAll() {
	msg.all = true
}

// MentionMember make mentioned someone in group
func (msg *Mention) MentionMember(member string) {
	msg.members = append(msg.members, member)
}

// MentionMobile make mentioned someone by mobile in group
func (msg *Mention) MentionMobile(mobile string) {
	msg.mobiles = append(msg.mobiles, mobile)
}

// Message implement Messager and build message only mention
func (msg *Mention) Message() []byte {
	return msg.payload().Build()
}

// payload build request payload to generate message
func (msg *Mention) payload() *payload {
	data := &payload{MessageType: "text", Text: &text{MentionMembers: msg.members, MentionMobiles: msg.mobiles}}
	if msg.all && len(data.Text.MentionMobiles) != 0 {
		data.Text.MentionMobiles = append(data.Text.MentionMobiles, "@all")
	} else if msg.all {
		data.Text.MentionMembers = append(data.Text.MentionMembers, "@all")
	}

	return data
}

// Text represents a text and mention message
type Text struct {
	*Mention

	content string
}

// Content sets the message content, only pure text, and max
func (msg *Text) Content(content string) error {
	if len(content) > TextMessageMaxLength {
		return ErrMessageTooLong
	}

	msg.content = content
	return nil
}

// Message implement Messager and build message with content and mention
func (msg *Text) Message() []byte {
	data := msg.payload()
	data.Text.Content = msg.content

	return data.Build()
}

// Markdown represents a pure markdown message
type Markdown struct {
	segments []string
}

// RawContent sets the raw markdown text
func (msg *Markdown) RawContent(raw string) error {
	if len(raw) > MarkdownMessageMaxLength {
		return ErrMessageTooLong
	}

	msg.segments = []string{raw}
	return nil
}

// payload represents a send request payload
// see https://work.weixin.qq.com/api/doc/90000/90136/91770
type payload struct {
	MessageType string    `json:"msgtype"`
	Text        *text     `json:"text,omitempty"`
	Markdown    *markdown `json:"markdown,omitempty"`
}

// Build build payload to json data
func (p *payload) Build() (bs []byte) {
	bs, _ = json.Marshal(p)
	return
}

// text represents text message data
// see https://work.weixin.qq.com/api/doc/90000/90136/91770
type text struct {
	Content        string   `json:"content"`
	MentionMembers []string `json:"mentioned_list,omitempty"`
	MentionMobiles []string `json:"mentioned_mobile_list,omitempty"`
}

// markdown represents markdown message data
// see https://work.weixin.qq.com/api/doc/90000/90136/91770
type markdown struct {
	Content string `json:"content"`
}
