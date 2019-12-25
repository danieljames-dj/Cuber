#include <iostream>
#include <fstream>
#include <cstdlib>
#include <time.h>
#include <cstdlib>
#define FILENAME "file.txt"

using namespace std;

string data[25][24][10];
int dataCount[25][24];

void readFile() {
	ifstream file;
	string line, word;
	int x, y;
	file.open(FILENAME);
	while (file.good()) {
		getline(file, line);
		// cout << line;
		if (line.length() >= 2) {
			x = line[0] - 'A';
			y = line[1] - 'A';
			dataCount[x][y] = 0;
		}
		word = "";
		for (int i = 2; i < line.length(); i++) {
			if (line[i] == ' ' || line.length() == i + 1) {
				if (line.length() == i + 1) word += line.substr(i,1);
				if (word.length() > 0) {
					// cout << word;
					data[x][y][dataCount[x][y]] = word;
					dataCount[x][y]++;
					word = "";
				}
			} else {
				// cout << line[i];
				word = word + line.substr(i,1);
				// cout << word;
			}
		}
	}
	// cout << data[0][0][0];
	file.close();
}

void writeFile() {
	ofstream file;
	file.open(FILENAME);
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 24; j++) {
			file << (char)('A' + i) << (char)('A' + j);
			for (int k = 0; k < dataCount[i][j]; k++) {
				file << " " << data[i][j][k];
			}
			file << "\n";
		}
	}
	file.close();
}

int showMenu() {
	int x;
	cout << "Options :"
	<< "\n\t1. Enter particular data"
	<< "\n\t2. Enter random data"
	<< "\n\t3. Get particular data"
	<< "\n\t4. Quiz"
	<< "\n\t5. Status"
	<< "\n\t6. Reset"
	<< "\nEnter option : ";
	cin >> x;
	return x;
}

void ipParticular() {
	string index, value;
	int x, y;
	cout << "Enter index : ";
	cin >> index;
	cout << "Enter value : ";
	cin >> value;
	x = index[0] - 'A';
	y = index[1] - 'A';
	data[x][y][dataCount[x][y]] = value;
	dataCount[x][y]++;
	writeFile();
}

void ipRandom() {
	int x, y;
	do {
		x = rand() % (25);
		y = rand() % (24);
	} while (dataCount[x][y] > 0);
	string value;
	cout << "Enter " << (char)('A' + x) << (char)('A' + y) << ": ";
	cin >> value;
	if (value == "0") return;
	data[x][y][dataCount[x][y]] = value;
	dataCount[x][y]++;
	writeFile();
}

void opParticular() {
	string index;
	int x, y;
	cout << "Enter index : ";
	cin >> index;
	x = index[0] - 'A';
	y = index[1] - 'A';
	for (int i = 0; i < dataCount[x][y]; i++) {
		cout << data[x][y][i] << " ";
	}
	cout << endl;
}

void quiz() {
	int n, c = 0, chances[25][24];
	cout << "Enter number of questions : ";
	cin >> n;
	time_t t1, t2;
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 24; j++) {
			chances[i][j] = 0;
		}
	}
	for (int i = 0; i < n; i++) {
		int x, y, p;
		cout << "Question " << i + 1 << "\n";
		srand(time(NULL));
		do {
			x = rand() % (25);
			y = rand() % (24);
		} while (chances[x][y] == 1 || dataCount[x][y] == 3);
		chances[x][y] = 1;
		time(&t1);
		cout << (char)('A' + x) << (char)('A' + y) << "? : ";
		cin.ignore();
		cin.ignore();
		time(&t2);
		cout << data[x][y][0] << ". Correct (1) or wrong (0)?";
		cout << " Time = " << t2 - t1 << " seconds";
		cin >> p;
		if (p) c++;
		if (dataCount[x][y] == 1) {
			dataCount[x][y] = 2;
			data[x][y][1] = "0";
		}
		if (p && t2 - t1 <= 1) {
			string t = data[x][y][1];
			if (t.compare("0") == 0) data[x][y][1] = "1";
			if (t.compare("1") == 0) data[x][y][1] = "2";
			if (t.compare("2") == 0) data[x][y][1] = "3";
			if (t.compare("3") == 0) data[x][y][1] = "4";
			if (t.compare("4") == 0) {
				data[x][y][1] = "5";
				dataCount[x][y] = 3;
				data[x][y][2] = "done";
				cout << "\n\n***MASTERED " << data[x][y][0] << "***\n\n";
				cout.flush();
			}
		} else if (p == 0) {
			data[x][y][1] = "0";
		}
	}
	cout << "Test completed with " << c << "/" << n <<"...\n";
	cout.flush();
	writeFile();
	readFile();
}

void status() {
	int freq[6];
	for (int i = 0; i < 6; i++) freq[i] = 0;
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 24; j++) {
			string t = data[i][j][1];
			if (t.compare("0") == 0) freq[0]++;
			if (t.compare("1") == 0) freq[1]++;
			if (t.compare("2") == 0) freq[2]++;
			if (t.compare("3") == 0) freq[3]++;
			if (t.compare("4") == 0) freq[4]++;
			if (t.compare("5") == 0) freq[5]++;
		}
	}
	cout << "Frequencies : \n";
	for (int i = 0; i < 6; i++) cout << i << " : " << freq[i] << endl;
}

void reset() {
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 24; j++) {
			data[i][j][1] = '0';
			dataCount[i][j] = 2;
		}
	}
	writeFile();
	readFile();
}

int main() {
	readFile();
	int x;
	while (1) {
		x = showMenu();
		switch(x) {
			case 1: ipParticular(); break;
			case 2: ipRandom(); break;
			case 3: opParticular(); break;
			case 4: quiz(); break;
			case 5: status(); break;
			case 6: reset(); break;
			default: return 0;
		}
	}
	return 0;
}
