class Solution(object):
    def twoSum(self, nums, target):
        return list([ (x, y) for x in range(len(nums)) for y in range(x, len(nums)) if nums[x] + nums[y] == target and (x != y) ][0])

if __name__ == '__main__':
    solution = Solution()
    print(solution.twoSum([3, 2, 4], 6))
    print(solution.twoSum([2, 7, 11, 15], 9))