#include "wokwi-api.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_BUFFER_SIZE       256
#define CMD_DISABLE_POLL      "AT DROFF\r"
#define CMD_ENGINE_RPM        "AT M10C\r"

typedef struct {
  uart_dev_t uart0;

  uint32_t attr_rpm;
  size_t cursor;
  char buffer[MAX_BUFFER_SIZE];
} chip_state_t;

static void on_uart_rx_data(void *user_data, uint8_t byte);
static void on_uart_write_done(void *user_data);
static bool ends_with(const char *str, size_t n, char *prefix);

void chip_init(void) {
  chip_state_t *chip = malloc(sizeof(chip_state_t));

  const uart_config_t uart_config = {
    .tx = pin_init("TX", INPUT_PULLUP),
    .rx = pin_init("RX", INPUT),
    .baud_rate = 115200,
    .rx_data = on_uart_rx_data,
    .write_done = on_uart_write_done,
    .user_data = chip,
  };

  chip->uart0 = uart_init(&uart_config);
  chip->cursor = 0;
  chip->attr_rpm = attr_init("rpm", 750);
  memset(chip->buffer, 0, MAX_BUFFER_SIZE);
}

static void on_uart_rx_data(void *user_data, uint8_t byte) {
  chip_state_t *chip = (chip_state_t*)user_data;

  chip->buffer[chip->cursor++] = byte;
  if (byte == '\r' || byte == '\n') {
    if (ends_with(chip->buffer, chip->cursor, CMD_DISABLE_POLL)) {
      uart_write(chip->uart0, (uint8_t*) "$DROFF$OK\n", 10);
    }

    if (ends_with(chip->buffer, chip->cursor, CMD_ENGINE_RPM)) {
      char out[32] = {0};
      sprintf(out, "%dRPM\n", attr_read(chip->attr_rpm));
      printf("RPM: %d\n", attr_read(chip->attr_rpm));

      uart_write(chip->uart0, (uint8_t*) out, (uint32_t) strlen(out));
    }

    chip->cursor = 0;
    memset(chip->buffer, 0, MAX_BUFFER_SIZE);
  }

  chip->cursor %= MAX_BUFFER_SIZE;
}

static void on_uart_write_done(void *user_data) {
  chip_state_t *chip = (chip_state_t*)user_data;
}

static bool ends_with(const char *str, size_t n, char *prefix) {
  if (str == NULL || prefix == NULL) return false;

  int si = n - 1, pi = strlen(prefix) - 1;
  while (pi >= 0 && str[si] == prefix[pi]) { si--; pi--; }
  return pi < 0;
}
