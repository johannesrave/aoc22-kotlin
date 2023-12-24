#!/bin/bash

# Directory of the script itself
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd)"

echo "$SCRIPT_DIR"

# Check if a two-digit number is provided as an argument
if [[ $# -eq 0 ]] || ! [[ $1 =~ ^[0-9]{2}$ ]]; then
    echo "Usage: $0 <two-digit-number>"
    exit 1
fi

# The argument
ARG=$1

# Placeholder to be replaced in templates
PLACEHOLDER="DAY_PLACEHOLDER"

# Paths of the template files (relative to the script)
DAY_TEMPLATE="$SCRIPT_DIR/templates/DayTemplate.kt" # Update with relative path
TEST_TEMPLATE="$SCRIPT_DIR/templates/DayTemplateTest.kt" # Update with relative path

# Destination file paths (relative to current command line path)
FILE1="input/${ARG}.txt"
FILE2="input/${ARG}.test.txt"
DAY_FILE="src/main/kotlin/Day${ARG}.kt"
TEST_FILE="src/test/kotlin/Day${ARG}Test.kt"

# Function to copy template and replace placeholder
copy_and_replace() {
    cp "$1" "$2"
    sed -i "s/${PLACEHOLDER}/${ARG}/g" "$2"
}

# Create files by copying templates and replacing placeholders
copy_and_replace "$DAY_TEMPLATE" "$DAY_FILE"
copy_and_replace "$TEST_TEMPLATE" "$TEST_FILE"
touch "$FILE1"
touch "$FILE2"

echo "Files created successfully."