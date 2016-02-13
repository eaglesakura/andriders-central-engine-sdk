#! /bin/sh


report_cp() {
mkdir "$CIRCLE_TEST_REPORTS/junit/"
mkdir "$CIRCLE_TEST_REPORTS/junit/debug"
mkdir "$CIRCLE_TEST_REPORTS/junit/release"

find . -type f -regex ".*/build/test-results/debug/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/debug/ \;
find . -type f -regex ".*/build/test-results/release/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/release/ \;
cp -r ./build/reports "$CIRCLE_ARTIFACTS"
}

# テスト実行
./gradlew test


if [ $? -ne 0 ]; then
    echo "UnitTest failed..."
    report_cp
    exit 1
else
    report_cp
fi
