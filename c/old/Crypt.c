#include "stdio.h"
#include "stdbool.h"
#include "string.h"
#include "ctype.h"
#define LEN 27

int PrepareKey(char *key);
void Encrypt(char *data, char const *key);
void Decrypt(char *data, char const *key);
static void GetKey(char *key);
static void GetData(char *data);

char * g_word = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

int main(void)
{
	char key[LEN];
	char strData[128];

	GetKey(key);		// Get Key

	if (PrepareKey(key))	//Initialize Key
	{
		puts("Initialize the key failure.");
		exit(1);
	}

	GetData(strData);	// Enter Text

	Encrypt(strData, key);		// Encrypt

	printf("Ciphertext : %s\n", strData);

	Decrypt(strData, key);

	printf("Original : %s\n", strData);

	return 0;
}

static void GetKey(char * key)
{
	puts("Entry a word as your key.");
	printf("Word : ");
	gets(key);
}

static void GetData(char *data)
{
	printf("\033[H\033[2J"); // Clear stdout
	puts("Key to initialize...");
	puts("Please remember your key.");
	printf("Text : ");
	gets(data);
}

int PrepareKey(char *key)
{
	char str[LEN] = { 0 };
	char *temp = key;
	int i = 0;
	int n = 0;

	strcpy(str, key);

	while (*key != '\0')
		if (!isalpha(*key++))
			return 1;

	key = temp;		// Pointer Init
	memset(key, 0, LEN);		// Key Init

	while (str[i] != '\0')
	{
		if (strchr(key, toupper(str[i])))
			i += 1;
		else
			*(key + n++) = toupper(str[i++]);
	}

	i = 0;
	temp = g_word;
	while (*(temp + i) != '\0')
	{
		if (strchr(key, *(temp + i)))
			i += 1;
		else
			*(key + n++) = *(temp + i++);
	}

	return 0;
}

void Encrypt(char *data, char const *key)
{
	char *pSkew;
	bool bLower = false;

	while (*data != '\0')
	{
		if (!isalpha(*data))
		{
			data += 1;
			continue;
		}

		if (islower(*data))
			bLower = true;

		pSkew=strchr(g_word,toupper(*data));
		*data = *(key + (pSkew - g_word));

		if (bLower)
			*data = tolower(*data);

		data += 1;
		bLower = false;
	}
}

void Decrypt(char *data, char const *key)
{
	char *pSkew;
	bool bLower = false;

	while (*data != '\0')
	{
		if (!isalpha(*data))
		{
			data += 1;
			continue;
		}

		if (islower(*data))
			bLower = true;

		pSkew = strchr(key, toupper(*data));
		*data = *(g_word + (pSkew - key));

		if (bLower)
			*data = tolower(*data);

		data += 1;
		bLower = false;
	}
}
