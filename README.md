# Illumio-Coding
Problem is to create a Firewall which will either accept or reject a packet based on the set of rules given to us in a CSV file.

Functions of the Firewall:
1. Read the rules from CSV.
2. Store them in some smart way.
3. Given a packet, compute if it should be accepted or not. (This needs to be done in some computationally efficient way)

To implement the third part, I chose to create a graph node structure where I would store the port number at the first level and in the second level I will store the IP address or IP ranges.
We could also use trie structure here (which I initially thought would be good), but it does not efficiently address the problem of ranges and complicates the node structure without much usefulness.

I spent about 70 minutes on coming up with the approach and coding it partially.

Team Preference:
1. Data Team 
2. Platform Team
