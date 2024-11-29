#!/bin/bash

# Initialize variables
YEAR=""
DAY=""
SESSION=""

# Parse command line options
while getopts ":y:d:s:" opt; do
    case ${opt} in
        y )
            YEAR=$OPTARG
            ;;
        d )
            DAY=$OPTARG
            ;;
        s )
            SESSION=$OPTARG
            ;;
        \? )
            echo "Invalid option: $OPTARG" 1>&2
            exit 1
            ;;
        : )
            echo "Invalid option: $OPTARG requires an argument" 1>&2
            exit 1
            ;;
    esac
done

# Check if all required parameters are provided
if [[ -z "$YEAR" || -z "$DAY" || -z "$SESSION" ]]; then
    echo "Usage: $0 -y <year> -d <day> -s <session>"
    echo "Example: $0 -y 2023 -d 1 -s your_session_cookie"
    exit 1
fi

# Construct the download URL
URL="https://adventofcode.com/${YEAR}/day/${DAY}/input"

# Create directory if it doesn't exist
# Use lib/src/main/resources/${year} as the target directory
TARGET_DIR="lib/src/main/resources/${YEAR}"
mkdir -p "${TARGET_DIR}"

# Filename for the input
FILENAME="${TARGET_DIR}/day${DAY}_input.txt"

# Download the file using wget
# -q: Quiet mode
# --no-check-certificate: Skip SSL verification (use cautiously)
# --header: Add session cookie
wget -q --no-check-certificate \
     --header="Cookie: session=${SESSION}" \
     -O "${FILENAME}" \
     "${URL}"

# Check if download was successful
if [ $? -eq 0 ]; then
    echo "Successfully downloaded input for Year ${YEAR}, Day ${DAY}"
    echo "Saved to: ${FILENAME}"
else
    echo "Failed to download input. Check your year, day, and session cookie."
    exit 1
fi
