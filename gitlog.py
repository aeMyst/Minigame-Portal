import os
import re
import subprocess
import argparse


class Commit:
    def __init__(self, commit_hash: str, author_name: str, commit_date: str, commit_message: str):
        self.commit_hash = commit_hash
        self.author_name = author_name
        self.commit_date = re.sub("'", "", commit_date)
        self.commit_message = '"' + commit_message + '"'

    def __str__(self):
        return f"{self.commit_hash} {self.author_name} {self.commit_date} {self.commit_message}"

def get_data() -> [str]:
    command = "git log --all --pretty=format:%H,%an,%ad,%s --date=format:'%Y-%m-%d'"
    output_b = subprocess.check_output(command, shell=True)
    output = output_b.decode("utf-8")
    return output.split("\n")


class Args:
    def __init__(self, author: str):
        self.author = author

    def __str__(self):
        return f"{self.author}"

def get_args() -> Args:
    # help: python gitlog.py [user (case insensitive)]
    parser = argparse.ArgumentParser(description="Filter git log by author")
    parser.add_argument("author", type=str, help="Author to filter by")

    args = parser.parse_args()
    return Args(args.author)

def main():
    args = get_args()
    author = args.author

    commit_regex: str = r"(?P<commit_hash>[a-f0-9]+),(?P<author_name>[^,]+),(?P<date>[^,]+),(?P<commit_message>.+)"

    # parse commits into Commit objects
    commits_as_str: [str] = get_data()
    commits: [Commit] = []
    for commit_str in commits_as_str:
        match = re.match(commit_regex, commit_str)
        if match:
            commit = Commit(match.group("commit_hash"), match.group("author_name"), match.group("date"), match.group("commit_message"))
            commits.append(commit)

    # filter by author
    commits_by_author = [commit for commit in commits if re.search(author, commit.author_name, re.IGNORECASE)]

    csvs: [str] = []
    for commit in commits_by_author:
        csvs.append(f"{commit.commit_hash},{commit.author_name},{commit.commit_date},{commit.commit_message}")

    csv_str = "\n".join(csvs)
    print(csv_str)
    with open("commits.csv", "w", encoding="utf-8") as file:
        file.write(csv_str)



if __name__ == "__main__":
    main()